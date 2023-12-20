package com.example.myapplication.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author lili
 * @Date 2023/12/15-17:26
 */
public class BondServiceActivity extends AppCompatActivity {
    private MyBoundService mMyBoundService;
    private boolean isConnectBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            mMyBoundService = myBinder.getServices();
            isConnectBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnectBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnectBound) {
            unbindService(serviceConnection);
            isConnectBound = false;
        }
    }
}
