package com.example.myapplication.network.retrofit;

import com.google.gson.Gson;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

/**
 * @Author lili
 * @Date 2023/12/13-16:14
 */
public interface IRetrofitConfig {
    String getBaseUrl();

    Gson getGson();

    OkHttpClient getOkHttpClient();

    Scheduler getSubScribeScheduler();
}
