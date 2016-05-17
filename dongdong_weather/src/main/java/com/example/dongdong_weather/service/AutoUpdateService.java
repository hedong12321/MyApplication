package com.example.dongdong_weather.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.dongdong_weather.MainActivity;
import com.example.dongdong_weather.R;
import com.example.dongdong_weather.receiver.AutoUpdateReceiver;
import com.example.dongdong_weather.util.Constant;
import com.example.dongdong_weather.util.HttpCallbackListener;
import com.example.dongdong_weather.util.HttpUtil;
import com.example.dongdong_weather.util.URLEncoderUtil;
import com.example.dongdong_weather.util.Utility;

/**
 * Created by dong.he on 2015/7/30.
 */
public class AutoUpdateService extends Service {

	/*
	@Override
	public void onCreate() {
		super.onCreate();
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentIntent(pi);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("东东天气已刷新");
        builder.setContentTitle("本来该显示城市名称的");
        builder.setContentText("本来该显示天气情况的");
        Notification notification = builder.build();
        startForeground(1, notification);
	}
	*/
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        
        Intent notifIntent = new Intent(this, MainActivity.class);
		PendingIntent notifPi = PendingIntent.getActivity(this, 0, notifIntent, 0);

		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentIntent(notifPi);
        builder.setSmallIcon(R.drawable.mango);
        builder.setTicker("东东天气已刷新");
        builder.setContentTitle(intent.getStringExtra("city_name"));
        builder.setContentText(intent.getStringExtra("city_weather"));
        Notification notification = builder.build();
        startForeground(1, notification);

        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 2 * 60 * 60 *1000; // 这是2小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息。
     */
    private void updateWeather() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String code = prefs.getString("preferenceCity", "");
        if (code != null && !"".equals(code) && !"null".equals(code)) {
        	SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.CHINA);
            String nowStr = sdf.format(new Date());

            String base = Constant.CONSTANT_URL
                    + "?areaid=" + code
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
                        Utility.handleWeatherResponse(AutoUpdateService.this, response);
                	}
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
