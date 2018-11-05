package com.android.orient.sports.happysports.application

import android.app.Application
import com.android.orient.practice.kldf.kldf.util.CacheUtil
import com.android.orient.practice.kldf.kldf.util.DeviceInfoUtil.instance
import com.android.orient.sports.happysports.utils.startAlarmService

/**
 * Package Name: com.android.orient.sports.happysports.application
 * Date: 2018/11/5 16:08
 * Author: zhanglei
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        CacheUtil.initCache(this)
        startAlarmService(this)
    }

}