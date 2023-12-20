package com.example.myapplication.network;

import com.example.myapplication.network.retrofit.RetrofitManager;
import com.example.myapplication.util.SchedulerManager;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * @Author lili
 * @Date 2023/12/13-16:08
 */
public interface IQueryDataInterface {

    IQueryDataInterface sIQueryDataInterface = RetrofitManager.getRetrofitManager().create(
        new FeelingRetrofitConfig(FeelingUrl.DATA, SchedulerManager.NETWORK),IQueryDataInterface.class
    );

    @GET("///")
    Observable<TaskResponse> getItemDates();

    @GET("///")
    Observable<TaskResponse> getItemDatesWithParams(@Body RequestBody requestBody);
}
