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
import com.example.dongdong_weather.util.Constant;
import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.HttpUtil;
import com.example.dongdong_weather.util.URLEncoderUtil;
import com.example.dongdong_weather.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        int countyCode = getIntent().getIntExtra("county_code", 0);
        // 有县级代号时就去查询天气
        if (countyCode > 0) {
            publishText.setText("更新中...");
            weatherInfoLayout.setVisibility(View.GONE);
            cityNameText.setVisibility(View.GONE);
            queryWeatherFromServer(countyCode);
        } else { // 没有县级代号时就直接显示本地天气
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name", ""));
        temp1Text.setText(prefs.getString("temp1", ""));
        temp2Text.setText(prefs.getString("temp2", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDateText.setText(prefs.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

        //Intent intent = new Intent(this, AutoUpdateService.class);
        //startService(intent);
    }
}
