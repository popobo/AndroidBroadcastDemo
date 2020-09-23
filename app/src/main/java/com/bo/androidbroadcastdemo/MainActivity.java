package com.bo.androidbroadcastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mBatteryLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        registerBatteryReceiver();
    }

    private void initListener() {
    }

    private void initView() {
        mBatteryLevel = findViewById(R.id.batteryLevel);
    }

    private void registerBatteryReceiver() {
        //2
        //要收听的频道：电量变化
        IntentFilter intentFilter = new IntentFilter();
        //3
        //设置频道
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        //4
        BatteryLevelReceiver batteryLevelReceiver = new BatteryLevelReceiver();
        //5
        //注册广播
        this.registerReceiver(batteryLevelReceiver, intentFilter);
    }

    /**
     * 1，创建一个广播接收者，继承自BroadcastReceiver (动态注册)
     */
    private class BatteryLevelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                if (mBatteryLevel != null) {
                    mBatteryLevel.setText("battery:" + (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) * 1.0f / maxLevel) * 100 + "percent");
                }
            } else if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
                Log.d(TAG, "ACTION_POWER_CONNECTED");
            } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                Log.d(TAG, "ACTION_POWER_DISCONNECTED");
            }
        }
    }
}