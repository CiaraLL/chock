package com.example.myapplication.network.retrofit;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @Author lili
 * @Date 2023/12/13-16:22
 */
public abstract class BaseRetrofitConfig implements IRetrofitConfig {
    private static final int TIME = 10;

    static class GsonHolder {
        static Gson gson = new Gson();
    }

    private static OkHttpClient sOkHttpClient = null;

    @Override
    public Gson getGson() {
        return GsonHolder.gson;
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (BaseRetrofitConfig.class) {
                if (sOkHttpClient == null) {
                    sOkHttpClient = createOkHttpClientBuilder(TIME).build();
                }
            }
        }
        return sOkHttpClient;
    }

    protected OkHttpClient.Builder createOkHttpClientBuilder(int time) {
        return new OkHttpClient.Builder()
                .connectTimeout(TIME, TimeUnit.SECONDS)
                .writeTimeout(TIME, TimeUnit.SECONDS)
                .readTimeout(TIME, TimeUnit.SECONDS);
    }

}
