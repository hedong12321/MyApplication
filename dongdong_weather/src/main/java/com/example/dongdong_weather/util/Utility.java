package com.example.dongdong_weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.dongdong_weather.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dong.he on 2015/7/29.
 */
public class Utility {

    private static final int BUFFER_SIZE = 1024;
    private static final String DB_NAME = "dongdong_weather.db"; //保存的数据库文件名
    private static final String PACKAGE_NAME = "com.example.dongdong_weather";//包名
    private static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/databases";  //在手机里存放数据库的位置

    /**
     * 判断是否已初始化数据库
     */
    public static boolean checkExists () {
        if (new File(DB_PATH + "/" + DB_NAME).exists()) {
            return true;
        }
        return false;
    }

    /**
     * 首次运行初始化数据库（从资源中复制到应用默认目录）
     */
    public static void initAreaCodeData (final Context context, final HttpCallbackListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File dir = new File(DB_PATH);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    InputStream is = context.getResources().openRawResource(R.raw.dongdong_weather); //欲导入的数据库
                    FileOutputStream fos = new FileOutputStream(DB_PATH + "/" + DB_NAME);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                    if (listener != null) {
                        // 回调onFinish()方法
                        listener.onFinish("初始化完成！");
                    }
                } catch (FileNotFoundException e) {
                    if (listener != null) {
                        // 回调onError()方法
                        listener.onError(e);
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        // 回调onError()方法
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");

            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");

            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
     */
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode,
                                       String temp1, String temp2, String weatherDesp, String publishTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", format.format(new Date()));
        editor.commit();
    }
}
