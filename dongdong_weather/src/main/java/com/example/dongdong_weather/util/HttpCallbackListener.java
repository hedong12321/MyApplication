package com.example.dongdong_weather.util;

/**
 * Created by dong.he on 2015/7/29.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
