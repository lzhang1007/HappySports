package com.android.orient.sports.happysports.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.orient.practice.kldf.base.SimpleServiceCallBack;
import com.android.orient.practice.kldf.kldf.util.CacheUtil;
import com.android.orient.sports.happysports.R;
import com.android.orient.sports.happysports.activity.ResultActivity;
import com.android.orient.sports.happysports.utils.DataUpdateUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by zhanglei on 2017/12/7.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;

    private static final int NOTIFICATIONS_ID = 1001;

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
//        String value = CacheUtil.getAppShared().getString("token_Update_time", "");
//        value += "\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date());
//        CacheUtil.putAppShared("token_Update_time", value);
        Log.d("AlarmReceiver", "OnReceive");

        sendService(context);
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

    private void sendService(final Context context) {
        DataUpdateUtil.sendStepService(new SimpleServiceCallBack() {

            @Override
            public void onSuccess(JSONObject jSONObject) {
                swapNotification(context, true);
                cancelAlarm(context);
                setAlarm(context, System.currentTimeMillis() + ONE_DAY);
                CacheUtil.putAppShared("receiver_update", "同步成功：" + "\n" + jSONObject);
            }

            @Override
            public void onFailed(String message) {
                swapNotification(context, false);
                CacheUtil.putAppShared("receiver_update", "同步失败：" + "\n" + message);
            }
        });
    }

    private void swapNotification(Context context, boolean isSuccess) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationO(context, isSuccess);
        } else {
            notification(context, isSuccess);
        }
    }

    private void notification(Context context, boolean isSuccess) {
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 0, new Intent(context, ResultActivity.class), 0);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.playa);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "alarmReceiver")
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSound(uri)
                        .setContentTitle("步数同步")
                        .setContentText(isSuccess ? "恭喜您，数据同步成功!" : "糟糕，数据同步失败，快来看看!")
                        .setContentIntent(contentIntent);

        if (mNotifyMgr != null) {
            mNotifyMgr.notify(NOTIFICATIONS_ID, mBuilder.build());
        }
    }

    private void notificationO(Context context, boolean isSuccess) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("alarmReceiver", "步数同步", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription(isSuccess ? "恭喜您，数据同步成功!" : "糟糕，数据同步失败，快来看看!");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

    }
}
