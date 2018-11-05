package com.android.orient.sports.happysports.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * @author zhanglei
 * @date 2017/12/7
 */
public class AlarmService extends Service {
    public static final String KEY_ACTION = "KEY_ACTION";
    public static final String KEY_HOUR = "KEY_HOUR";
    public static final String KEY_MINUTE = "KEY_MINUTE";

    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AlarmService", "start service");
        onStart(intent);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        onStart(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onStart(Intent intent) {
        if (null != intent) {
            int state = intent.getIntExtra(KEY_ACTION, 0);
            if (0 == state) {
                int hour = intent.getIntExtra(KEY_HOUR, 0);
                int minute = intent.getIntExtra(KEY_MINUTE, 0);
                alarm.cancelAlarm(this);
                alarm.setAlarm(this, calculateTime(hour, minute));
                Toast.makeText(this, "定时器已经开启", Toast.LENGTH_SHORT).show();
            } else {
                alarm.cancelAlarm(this);
                Toast.makeText(this, "定时器已经关闭", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long calculateTime(int hour, int minute) {
        Date date = new Date();
        int curHour = date.getHours();
        int curMinute = date.getMinutes();
        int curSeconds = curHour * 60 * 60 * 1000 + curMinute * 60 * 1000;
        int seconds = hour * 60 * 60 * 1000 + minute * 60 * 1000;
        if (seconds < curSeconds) {
            return System.currentTimeMillis() + 24 * 60 * 60 * 1000 + seconds - curSeconds;
        } else {
            return System.currentTimeMillis() + seconds - curSeconds;
        }
    }

    @Override
    public void onDestroy() {
        alarm.cancelAlarm(this);
        super.onDestroy();
    }
}
