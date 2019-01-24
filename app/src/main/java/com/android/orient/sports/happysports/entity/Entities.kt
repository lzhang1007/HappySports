package com.android.orient.sports.happysports.entity

import android.annotation.SuppressLint
import com.android.orient.sports.happysports.encrypt.encodeSMS4toString
import com.android.orient.sports.happysports.http.URL_LOGIN
import com.android.orient.sports.happysports.http.URL_SYC_STEP
import com.android.orient.sports.happysports.utils.MIEI
import com.android.orient.sports.happysports.utils.appVersion
import okhttp3.FormBody
import okhttp3.Request
import java.text.SimpleDateFormat

/**
 * @PackageName com.android.orient.sports.happysports.entity
 * @date 2019/1/24 9:46
 * @author zhanglei
 */
abstract class NetWorkRequest {
    private val rb: Request.Builder by lazy { setupBuilder() }

    abstract fun setupBuilder(): Request.Builder
    /**
     * 获取Request
     */
    open fun getRequest(): Request = rb.build()

    /**
     * addHeader
     */
    open fun addHeader(key: String, value: String) {
        rb.addHeader(key, value)
    }
}

/**
 * 登录
 */
class LoginRequest(private val userName: String, private val password: String) : NetWorkRequest() {

    override fun setupBuilder(): Request.Builder {
        val builder = FormBody.Builder()
                .add("userCode", userName)
                .add("password", encodeSMS4toString(password, "com.dfzq.kldf"))
                .add("deviceName", "Android")//设备名称（机型），如iPhone6s XiaoMi 3
                .add("brandName", android.os.Build.MANUFACTURER)//品牌名称
                .add("appVersion", appVersion)//应用版本号
                .add("osVersion", android.os.Build.VERSION.RELEASE)//操作系统版本
                .add("os", "android_phone")//系统类型，有ios_phone,ios_pad,android_pad,android_phone,windows,linux,mac
                .add("deviceId", MIEI)//设备唯一id
                .add("deviceToken", MIEI)//ios系统用，其他系统跟deviceId一致即可
                .add("hasEncrypt", "1")//是否加密

        return Request.Builder()
                .url(URL_LOGIN)
                .post(builder.build())
                .tag("LoginRequest")
    }
}

/**
 * 同步步数
 */
class StepRequest(private val step: String, private val vhsToken: String) : NetWorkRequest() {
    @SuppressLint("SimpleDateFormat")
    override fun setupBuilder(): Request.Builder {
        val builder = FormBody.Builder()
                .add("timestamp", System.currentTimeMillis().toString())
                .add("appVersion", appVersion)//应用版本号
                .add("osVersion", android.os.Build.VERSION.RELEASE)//操作系统版本
                .add("deviceId", MIEI.split("#")[2])//设备唯一id
                .add("stride", "7.055E-4")
                .add("steps", "[{\"sportDate\":\"" + SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()) + "\",\"flag\":\"add\",\"step\":" + step + ",\"handMac\":\"0\"}]")

        return Request.Builder()
                .url(URL_SYC_STEP)
                .addHeader("vhstoken", vhsToken)
                .post(builder.build())
                .tag("DfzqAddStepRequest")
    }
}

