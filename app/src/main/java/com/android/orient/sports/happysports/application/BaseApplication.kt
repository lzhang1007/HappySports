package com.android.orient.sports.happysports.application

import android.app.Application
import com.android.orient.sports.happysports.utils.ContextDelegate
import com.android.orient.sports.happysports.utils.startAlarmService

/**
 * Package Name: com.android.orient.sports.happysports.application
 * Date: 2018/11/5 16:08
 * Author: zhanglei
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextDelegate.context = this
        startAlarmService(this)
    }

}