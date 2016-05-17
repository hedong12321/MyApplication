package com.example.dongdong_weather.util;

import android.app.Application;
import android.content.Context;

/**
 * 提供系统全局Context获取方法
 * @author dong.he
 */
public class MyApplication extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
