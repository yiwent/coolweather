package com.yiwen.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-03
 * Time: 22:23
 * FIXME
 */
public class HttpUtil {
    public static void sendOkhttpReques(String addres, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(addres).build();
        client.newCall(request);
    }
}
