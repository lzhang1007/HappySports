package com.android.orient.sports.happy.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson

/**
 * @PackageName com.android.orient.sports.happysports.utils
 * @date 2019/1/24 8:58
 * @author zhanglei
 */
val appContext: Context by lazy { ContextDelegate.context }

const val MIEI = "#com.dfzq.kldf#867982021702619"

val gson = Gson()

@SuppressLint("StaticFieldLeak")
object ContextDelegate {
    lateinit var context: Context
}
