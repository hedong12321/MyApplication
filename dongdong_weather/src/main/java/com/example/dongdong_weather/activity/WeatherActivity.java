package com.example.dongdong_weather.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dongdong_weather.R;
import com.example.dongdong_weather.db.WeatherDB;
import com.example.dongdong_weather.model.AreaCode;
import com.example.dongdong_weather.model.WeatherCode;
import com.example.dongdong_weather.model.WeatherHistory;
import com.example.dongdong_weather.util.Constant;
import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.HttpUtil;
import com.example.dongdong_weather.util.URLEncoderUtil;
import com.example.dongdong_weather.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dong.he on 2015/7/30.
 */
public class WeatherActivity extends Activity {

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

    int countyCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        // 初始化各控件
        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.title_text);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);

        countyCode = getIntent().getIntExtra("county_code", 0);
        // 有县级代号时就去查询天气
        if (countyCode > 0) {
            publishText.setText("更新中...");
            weatherInfoLayout.setVisibility(View.GONE);
            cityNameText.setVisibility(View.GONE);
            queryWeatherFromServer(countyCode);
        } else { // 没有城市代号时就直接查询天气历史
            showWeather();
        }
    }

    /**
     * 向服务器查询天气信息
     */
    private void queryWeatherFromServer(final int countyCode) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
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
                // 处理服务器返回的天气信息
                Utility.handleWeatherResponse(WeatherActivity.this, response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        String today = sdf.format(new Date());

        WeatherDB db = WeatherDB.getInstance(this);
        int code = countyCode;
        if (code <= 0) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            code = Integer.valueOf(prefs.getString("preferenceCity", ""));
        }
        WeatherHistory history = db.getWeatherHistoryByAreaIdAndDate(String.valueOf(code), today);
        if (history != null) {
            AreaCode areaCode = db.getCityByAreaId(String.valueOf(code));
            WeatherCode weatherCode = db.getWeatherCodeByWeatherNo(String.valueOf(history.getDayWeatherNo()));

            cityNameText.setText(areaCode.getNameZh());
            temp1Text.setText(history.getDayTemperature() != null ? history.getDayTemperature().toString() : "0");
            temp2Text.setText(history.getNightTemperature() != null ? history.getNightTemperature().toString() : "0");
            weatherDespText.setText(weatherCode.getNameZh());
            publishText.setText("今天" + history.getPublishTime() + "发布");
            currentDateText.setText(format.format(new Date()));
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
        }
        else {
            queryWeatherFromServer(code);
        }

        //Intent intent = new Intent(this, AutoUpdateService.class);
        //startService(intent);
    }
}
