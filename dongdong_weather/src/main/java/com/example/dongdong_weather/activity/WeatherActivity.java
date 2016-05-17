package com.example.dongdong_weather.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.dongdong_weather.MainActivity;
import com.example.dongdong_weather.R;
import com.example.dongdong_weather.custom.SlidingMenu;
import com.example.dongdong_weather.custom.TitleBuilder;
import com.example.dongdong_weather.db.WeatherDB;
import com.example.dongdong_weather.model.AreaCode;
import com.example.dongdong_weather.model.WeatherCode;
import com.example.dongdong_weather.model.WeatherHistory;
import com.example.dongdong_weather.service.AutoUpdateService;
import com.example.dongdong_weather.util.Constant;
import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.HttpUtil;
import com.example.dongdong_weather.util.URLEncoderUtil;
import com.example.dongdong_weather.util.Utility;

/**
 * Created by dong.he on 2015/7/30.
 */
public class WeatherActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

	private static final int REFRESH_COMPLETE = 0X110;
	
	/* 滑动菜单 */
	private SlidingMenu mMenu;
	/* 下拉刷新控件 */
	private SwipeRefreshLayout mSwipeLayout;
    private LinearLayout weatherInfoLayout;

    /**
     *  用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private TextView publishText;
    /**
     * 用于显示天气描述信息
     */
    private TextView weatherDespText;
    /**
     * 用于显示气温1
     */
    private TextView temp1Text;
    /**
     * 用于显示气温2
     */
    private TextView temp2Text;
    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;

    String countyCode = "";
    
    // 回调，在主线程中更新UI等
    private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case REFRESH_COMPLETE:
				// 更新天气
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
				String code = prefs.getString("preferenceCity", "");
		        if (code != null && !"".equals(code) && !"null".equals(code)) {
		        	queryWeatherFromServer(code);
		        }
		        else {
		        	Toast.makeText(WeatherActivity.this, "还没有选择城市！", Toast.LENGTH_LONG).show();
		        }
				
				mSwipeLayout.setRefreshing(false);
				break;
			}
		};
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        // 初始化各控件
        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.title_middle_textview);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);

        /**
         * 初始化标题
         */
        initTitle();
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
		//mSwipeLayout.setProgressViewEndTarget(true, 100);
		
		
        
        countyCode = getIntent().getStringExtra("county_code");
        // 有县级代号时就去查询天气
        if (countyCode != null && !"".equals(countyCode) && !"null".equals(countyCode)) {
            publishText.setText("更新中...");
            weatherInfoLayout.setVisibility(View.GONE);
            queryWeatherFromServer(countyCode);
        } else { // 没有城市代号时就直接查询天气历史
            showWeather();
        }
    }
    
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件
         * 2.设置中间TextView显示的文字
         * 3.设置右边的图片按钮显示，并设置事件
         */
    	new TitleBuilder(this)
    			.setLeftImageRes(R.drawable.layout_right_menu)
    			.setLeftTextOrImageListener(leftCilckListener)
    	        .setRightText("其他城市")
    	        .setRightTextOrImageListener(rightCilckListener);

    }
    
    private View.OnClickListener leftCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**
             * 也可根据上面的配置进行监听 此处做演示使用
             */
            if (v.getId() == R.id.title_left_imageview) {
                /**
                 * 左边图片点击动作-打开或关闭滑动菜单
                 */
            	mMenu.toggle();
            }
        }
    };
    private View.OnClickListener rightCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.title_right_textview) {
                /**
                 * 右边文字点击动作-调用选择城市的activity
                 */
            	Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
            }
        }
    };

    /**
     * 向服务器查询天气信息
     */
    private void queryWeatherFromServer(final String countyCode) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.CHINA);
        String nowStr = sdf.format(new Date());

        String base = Constant.CONSTANT_URL
                + "?areaid=" + countyCode
                + "&type=" + Constant.DATA_TYPE_FORECAST_V
                + "&date=" + nowStr;

        //需要加密的数据
        String data = base + "&appid=" + Constant.APPID;
        String address = base + "&appid=" + Constant.APPID.substring(0, 6)
                + "&key=" + URLEncoderUtil.standardURLEncoder(data, Constant.PRIVATE_KEY);

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
            	if (response != null && !"".equals(response) && !"null".equals(response)) {
            		// 处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                            Toast.makeText(WeatherActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
            	}
            	else {
            		runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishText.setText("更新失败");
                        }
                    });
            	}
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("更新失败");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    private void showWeather() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        String today = sdf.format(new Date());

        WeatherDB db = WeatherDB.getInstance(this);
        String code = countyCode;
        if (code == null || "".equals(code) || "null".equals(code)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            code = prefs.getString("preferenceCity", "");
        }
        WeatherHistory history = db.getWeatherHistoryByAreaIdAndDate(code, today);
        if (history != null) {
            AreaCode areaCode = db.getCityByAreaId(code);
            String dayWeatherNo = history.getDayWeatherNo();
            String defaultWeather = "多云";
            if (dayWeatherNo != null && !"".equals(dayWeatherNo) && !"null".equals(dayWeatherNo)) {
            	WeatherCode weatherCode = db.getWeatherCodeByWeatherNo(dayWeatherNo);
            	defaultWeather = weatherCode.getNameZh();
            }

            cityNameText.setText(areaCode.getNameZh());
            temp1Text.setText(history.getDayTemperature() != null ? history.getDayTemperature() : "0");
            temp2Text.setText(history.getNightTemperature() != null ? history.getNightTemperature() : "0");
            weatherDespText.setText(defaultWeather);
            publishText.setText("发布时间：" + history.getPublishTime());
            currentDateText.setText(format.format(new Date()));
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
            
            // 开启后台服务-自动更新天气
            Intent intent = new Intent(this, AutoUpdateService.class);
            intent.putExtra("city_name", areaCode.getNameZh());
            intent.putExtra("city_weather", defaultWeather + " " + history.getDayTemperature()
            		+ "℃~" + history.getNightTemperature() + "℃");
            startService(intent);
        }
        else {
            queryWeatherFromServer(code);
        }

        //Intent intent = new Intent(this, AutoUpdateService.class);
        //startService(intent);
    }

    /**
     * 下拉刷新
     */
	@Override
	public void onRefresh() {
		//mSwipeLayout.setRefreshing(true);
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 3000);
	}
}
