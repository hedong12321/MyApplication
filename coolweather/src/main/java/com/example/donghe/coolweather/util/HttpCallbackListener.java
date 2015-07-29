package com.example.donghe.coolweather.util;

/**
 * Created by dong.he on 2015/7/29.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
