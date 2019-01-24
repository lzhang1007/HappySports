package com.android.orient.sports.happysports.utils

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