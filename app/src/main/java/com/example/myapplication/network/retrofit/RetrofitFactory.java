package com.example.myapplication.network.retrofit;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author lili
 * @Date 2023/12/13-16:13
 */
public class RetrofitFactory {

    public static Retrofit create(@NonNull IRetrofitConfig retrofitConfig) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(retrofitConfig.getOkHttpClient())
                .baseUrl(retrofitConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create());
        if (retrofitConfig.getSubScribeScheduler() != null) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(retrofitConfig.getSubScribeScheduler()));
        } else {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        return builder.build();
    }
}
