package com.android.orient.sports.happysports.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by zhanglei on 2017/12/7.
 */

public class AlarmService extends Service {
    AlarmReceiver alarm = new AlarmReceiver();

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zhanglei", "start service");
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alarm.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        alarm.cancelAlarm(this);
        super.onDestroy();
    }
}
