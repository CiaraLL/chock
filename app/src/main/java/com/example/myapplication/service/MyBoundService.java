package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @Author lili
 * @Date 2023/12/15-17:19
 */
public class MyBoundService extends Service {
    private final MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        MyBoundService getServices() {
            return MyBoundService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int add(int a, int b) {
        return a + b;
    }
}
