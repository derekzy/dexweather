package com.derekzy.dexweather.util;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
