package com.android.orient.sports.happysports.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.android.orient.practice.kldf.kldf.util.CacheUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhanglei on 2017/12/7.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final long ONE_DAY = 24 * 60 * 60 * 1000 / 12;

    @SuppressLint("WakelockTimeout")
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            if (wl != null) {
                wl.acquire();
                wl.release();
            }
        }
        String value = CacheUtil.getAppShared().getString("token_Update_time", "");
        value += "\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date());
        CacheUtil.putAppShared("token_Update_time", value);
        Log.d("AlarmReceiver", "OnReceive");
    }

    public void setAlarm(Context context, long triggerAtMillis) {
        Log.d("AlarmReceiver", "triggerAtTime = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date(triggerAtMillis)));
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        if (am != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, ONE_DAY, pi); // Millisec * Second * Minute
        }
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(sender);
        }
    }
}
