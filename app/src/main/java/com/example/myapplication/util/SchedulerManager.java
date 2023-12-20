package com.example.myapplication.util;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author lili
 * @Date 2023/12/13-17:09
 */
public class SchedulerManager {

    public static final Scheduler MAIN = AndroidSchedulers.mainThread();

    public static final Scheduler NETWORK = Schedulers.from(Executors.newFixedThreadPool(4));
}
