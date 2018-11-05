package com.android.orient.sports.happysports.utils

import android.content.Context
import android.content.Intent
import com.android.orient.practice.kldf.kldf.util.CacheUtil
import com.android.orient.sports.happysports.alarm.AlarmService

/**
 * Package Name: com.android.orient.sports.happysports.utils
 * Date: 2018/11/5 16:13
 * Author: zhanglei
 */

fun startAlarmService(context: Context) {
    val state = CacheUtil.getAppShared().getBoolean("TimePickerState", false)
    if (state) {
        val hour = CacheUtil.getAppShared().getInt("SET_HOURS", 21)
        val minute = CacheUtil.getAppShared().getInt("SET_MINUTES", 0)
        val intent = Intent(context, AlarmService::class.java)
        intent.putExtra(AlarmService.KEY_HOUR, hour)
        intent.putExtra(AlarmService.KEY_MINUTE, minute)
        intent.putExtra(AlarmService.KEY_ACTION, 0)
        context.startService(intent)
    }
}