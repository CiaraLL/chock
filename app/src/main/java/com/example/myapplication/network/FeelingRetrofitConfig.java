package com.example.myapplication.network;

import com.example.myapplication.network.retrofit.BaseRetrofitConfig;
import com.example.myapplication.util.FeelingUrlUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author lili
 * @Date 2023/12/13-16:28
 */
public class FeelingRetrofitConfig extends BaseRetrofitConfig {
    private FeelingUrl mFeelingUrl;
    private Scheduler mScheduler;

    public FeelingRetrofitConfig(FeelingUrl mFeelingUrl, Scheduler mScheduler) {
        this.mFeelingUrl = mFeelingUrl;
        this.mScheduler = mScheduler;
    }

    @Override
    public String getBaseUrl() {
        return FeelingUrlUtil.convertUrl(mFeelingUrl);
    }

    @Override
    public Scheduler getSubScribeScheduler() {
        return mScheduler;
    }

    @Override
    public OkHttpClient.Builder createOkHttpClientBuilder(int time) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(getBaseUrlReplaceInterceptor());

        return super.createOkHttpClientBuilder(time);
    }

    private Interceptor getBaseUrlReplaceInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                builder.removeHeader("baseUrl");

                HttpUrl oldUrl = request.url();
                HttpUrl newUrl = oldUrl.newBuilder().host("..")
                        .build();

                Request newRequest = builder.url(newUrl).build();
                return chain.proceed(newRequest);
            }
        };
    }
}
