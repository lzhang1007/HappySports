package com.android.orient.sports.happy.alarm

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.android.orient.sports.happy.R
import com.android.orient.sports.happy.activity.ResultActivity
import com.android.orient.sports.happy.http.loginAndSyncStep
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zhanglei
 * @date 2017/12/7
 */
class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("WakelockTimeout")
    override fun onReceive(context: Context, intent: Intent) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Time:Tag")
        if (wl != null) {
            wl.acquire()
            wl.release()
        }
        Log.d("AlarmReceiver", "OnReceive")

        sendService(context)
    }

    fun setAlarm(context: Context, triggerAtMillis: Long) {
        Log.d("AlarmReceiver", "triggerAtTime = " + SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(Date(triggerAtMillis)))
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)
        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, ONE_DAY, pi)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }

    private fun sendService(context: Context) {
        loginAndSyncStep(success = {
            swapNotification(context, true)
            cancelAlarm(context)
            setAlarm(context, System.currentTimeMillis() + ONE_DAY)
        }, failure = {
            swapNotification(context, false)
        })
    }

    private fun swapNotification(context: Context, isSuccess: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationO(context, isSuccess)
        } else {
            notification(context, isSuccess)
        }
    }

    private fun notification(context: Context, isSuccess: Boolean) {
        val mNotifyMgr = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(
                context, 0, Intent(context, ResultActivity::class.java), 0)
        val uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.playa)
        val mBuilder = NotificationCompat.Builder(context, "alarmReceiver")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(uri)
                .setContentTitle("步数同步")
                .setContentText(if (isSuccess) "恭喜您，数据同步成功!" else "糟糕，数据同步失败，快来看看!")
                .setContentIntent(contentIntent)

        mNotifyMgr.notify(NOTIFICATIONS_ID, mBuilder.build())
    }

    private fun notificationO(context: Context, isSuccess: Boolean) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("alarmReceiver", "步数同步", NotificationManager.IMPORTANCE_DEFAULT)

            // Configure the notification channel.
            notificationChannel.description = if (isSuccess) "恭喜您，数据同步成功!" else "糟糕，数据同步失败，快来看看!"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {
        private const val ONE_DAY = (24 * 60 * 60 * 1000).toLong()

        private const val NOTIFICATIONS_ID = 1001
    }
}
