package com.android.orient.sports.happy.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @PackageName com.android.orient.sports.happysports.utils
 * @date 2019/1/24 10:35
 * @author zhanglei
 */
const val SP_NAME = "happy_sports_sp"


object SPDelegates : SharedPreferences by appContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

private const val KEY_AUTH = "key_auth"
var authToken: String
    get() = SPDelegates.getString(KEY_AUTH, "") ?: ""
    set(value) = SPDelegates.edit().putString(KEY_AUTH, value).apply()

private const val KEY_ACCESS = "key_access"
var accessToken: String
    get() = SPDelegates.getString(KEY_ACCESS, "") ?: ""
    set(value) = SPDelegates.edit().putString(KEY_ACCESS, value).apply()

private const val KEY_VHS_TOKEN = "key_vhs_token"
var vhsToken: String
    get() = SPDelegates.getString(KEY_VHS_TOKEN, "6284da480bd4469fb9e8709f21625bfd")
            ?: "6284da480bd4469fb9e8709f21625bfd"
    set(value) = SPDelegates.edit().putString(KEY_VHS_TOKEN, value).apply()

private const val KEY_USER_NAME = "key_user_name"
var userName: String
    get() = SPDelegates.getString(KEY_USER_NAME, "") ?: ""
    set(value) = SPDelegates.edit().putString(KEY_USER_NAME, value).apply()

private const val KEY_PASSWORD = "key_password"
var password: String
    get() = SPDelegates.getString(KEY_PASSWORD, "") ?: ""
    set(value) = SPDelegates.edit().putString(KEY_PASSWORD, value).apply()

private const val KEY_LOGIN_DATE = "key_login_date"
var loginDate: String
    get() = SPDelegates.getString(KEY_LOGIN_DATE, System.currentTimeMillis().toString())
            ?: System.currentTimeMillis().toString()
    set(value) = SPDelegates.edit().putString(KEY_LOGIN_DATE, value).apply()

private const val KEY_APP_VERSION = "key_app_version"
var appVersion: String
    get() = SPDelegates.getString(KEY_APP_VERSION, "3.1.0") ?: "3.1.0"
    set(value) = SPDelegates.edit().putString(KEY_APP_VERSION, value).apply()

private const val KEY_ALARM_HOUR = "key_alarm_hour"
var alarmHour: Int
    get() = SPDelegates.getInt(KEY_ALARM_HOUR, 22)
    set(value) = SPDelegates.edit().putInt(KEY_ALARM_HOUR, value).apply()

private const val KEY_ALARM_MINUTE = "key_alarm_minute"
var alarmMinute: Int
    get() = SPDelegates.getInt(KEY_ALARM_MINUTE, 0)
    set(value) = SPDelegates.edit().putInt(KEY_ALARM_MINUTE, value).apply()

private const val KEY_ALARM_ON = "key_alarm_on"
var alarmOn: Boolean
    get() = SPDelegates.getBoolean(KEY_ALARM_ON, true)
    set(value) = SPDelegates.edit().putBoolean(KEY_ALARM_ON, value).apply()
