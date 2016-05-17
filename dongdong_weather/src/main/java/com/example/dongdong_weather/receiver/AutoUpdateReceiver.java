package com.example.dongdong_weather.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dongdong_weather.db.WeatherDB;
import com.example.dongdong_weather.model.AreaCode;
import com.example.dongdong_weather.model.WeatherCode;
import com.example.dongdong_weather.model.WeatherHistory;
import com.example.dongdong_weather.service.AutoUpdateService;
import com.example.dongdong_weather.util.MyApplication;

/**
 * Created by dong.he on 2015/7/30.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		String code = prefs.getString("preferenceCity", "");
        if (code != null && !"".equals(code) && !"null".equals(code)) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            String today = sdf.format(new Date());
            WeatherDB db = WeatherDB.getInstance(MyApplication.getContext());
            WeatherHistory history = db.getWeatherHistoryByAreaIdAndDate(code, today);
            if (history != null) {
            	AreaCode areaCode = db.getCityByAreaId(code);
                String dayWeatherNo = history.getDayWeatherNo();
                String defaultWeather = "多云";
                if (dayWeatherNo != null && !"".equals(dayWeatherNo) && !"null".equals(dayWeatherNo)) {
                	WeatherCode weatherCode = db.getWeatherCodeByWeatherNo(dayWeatherNo);
                	defaultWeather = weatherCode.getNameZh();
                }
                
                i.putExtra("city_name", areaCode.getNameZh());
                i.putExtra("city_weather", defaultWeather + " " + history.getDayTemperature()
                		+ "℃~" + history.getNightTemperature() + "℃");
            }
        }
        
        context.startService(i);
    }
}
