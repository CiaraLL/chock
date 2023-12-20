package com.example.myapplication.network.retrofit;

import retrofit2.Retrofit;

/**
 * @Author lili
 * @Date 2023/12/13-16:13
 */
public class RetrofitManager {
    private static RetrofitManager sRetrofitManager = new RetrofitManager();

    public static RetrofitManager getRetrofitManager() {
        return sRetrofitManager;
    }

    public <T> T create(IRetrofitConfig retrofitConfig, Class<T> type) {
        Retrofit retrofit = RetrofitFactory.create(retrofitConfig);
        return retrofit.create(type);
    }
}
