package com.xoshop.utils;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Map;


/**
 * Created by xiaoqiang on 2017/12/11.
 */

public class OkhttpUtil {
    public static OkHttpClient instance;

    public static void onPost(String url, Map<String, String> params, Callback callback) {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        com.squareup.okhttp.RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        //3，创建call对象并将请求对象添加到调度中
        instance.newCall(request).enqueue(callback);
    }

    public static void onGet(String url, Callback callback) {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = instance.newCall(request);
        call.enqueue(callback);
    }
}
