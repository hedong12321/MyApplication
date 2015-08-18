package com.example.dongdong_weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.dongdong_weather.R;
import com.example.dongdong_weather.db.WeatherDB;
import com.example.dongdong_weather.model.AreaExtInfo;
import com.example.dongdong_weather.model.WeatherHistory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     * 解析服务器返回的JSON数据，并将解析出的数据存储到数据库
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("c");

            AreaExtInfo extInfo = new AreaExtInfo();
            int areaId = Integer.valueOf(weatherInfo.getString("c1"));
            extInfo.setAreaId(areaId);
            extInfo.setCityOrder(Integer.valueOf(weatherInfo.getString("c10")));
            extInfo.setCityAreaCode(weatherInfo.getString("c11"));
            extInfo.setPostcode(weatherInfo.getString("c12"));
            extInfo.setLatitude(Float.valueOf(weatherInfo.getString("c13")));
            extInfo.setLongitude(Float.valueOf(weatherInfo.getString("c14")));
            extInfo.setAltitude(Float.valueOf(weatherInfo.getString("c15")));
            extInfo.setRadarNo(weatherInfo.getString("c16"));
            extInfo.setTimeZone(weatherInfo.getString("c17"));

            JSONObject weather = jsonObject.getJSONObject("f");

            String publishTime = weather.getString("f0");

            JSONArray weatherArr = weather.getJSONArray("f1");
            WeatherHistory[] histories = new WeatherHistory[weatherArr.length()];
            WeatherHistory his = null;

            // 处理日期
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (int i = 0; i < weatherArr.length(); i++) {
                JSONObject info = weatherArr.getJSONObject(i);

                his = new WeatherHistory();
                his.setAreaId(areaId);
                his.setForecastDate(sdf.format(calendar.getTime()));
                if (!TextUtils.isEmpty(info.getString("fa"))) {
                    his.setDayWeatherNo(Integer.valueOf(info.getString("fa")));
                }
                if (!TextUtils.isEmpty(info.getString("fb"))) {
                    his.setNightWeatherNo(Integer.valueOf(info.getString("fb")));
                }
                if (!TextUtils.isEmpty(info.getString("fc"))) {
                    his.setDayTemperature(Integer.valueOf(info.getString("fc")));
                }
                if (!TextUtils.isEmpty(info.getString("fd"))) {
                    his.setNightTemperature(Integer.valueOf(info.getString("fd")));
                }
                if (!TextUtils.isEmpty(info.getString("fe"))) {
                    his.setDayWindDirNo(Integer.valueOf(info.getString("fe")));
                }
                if (!TextUtils.isEmpty(info.getString("ff"))) {
                    his.setNightWindDirNo(Integer.valueOf(info.getString("ff")));
                }
                if (!TextUtils.isEmpty(info.getString("fg"))) {
                    his.setDayWindForceNo(Integer.valueOf(info.getString("fg")));
                }
                if (!TextUtils.isEmpty(info.getString("fh"))) {
                    his.setNightWindForceNo(Integer.valueOf(info.getString("fh")));
                }
                his.setSunriseSunset(info.getString("fi"));
                his.setPublishTime(publishTime);

                histories[i] = his;
                calendar.add(Calendar.DATE, 1);
            }

            // 调用数据库方法判断是否存在，进行插入或更新
            if (weatherInfo != null) {
                WeatherDB db = WeatherDB.getInstance(context);
                if (db.getAreaExtInfoByAreaId(String.valueOf(extInfo.getAreaId())) != null) {
                    db.updateAreaExtInfo(extInfo);
                }
                else {
                    db.addAreaExtInfo(extInfo);
                }

                for (WeatherHistory h : histories) {
                    if (db.getWeatherHistoryByAreaIdAndDate(String.valueOf(h.getAreaId()), h.getForecastDate()) != null) {
                        db.updateWeatherHistory(h);
                    }
                    else {
                        db.saveWeatherHistory(h);
                    }
                }
            }

            // 数据库更新后保存已选城市的areaId到SharedPreferences，用以启动程序时判断
            // 该进入哪一个activity，判断则在MainActivity中进行
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            // 如果没有添加首先城市就添加当前城市为首先城市
            if (TextUtils.isEmpty(prefs.getString("preferenceCity", ""))) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("preferenceCity", String.valueOf(areaId));
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
