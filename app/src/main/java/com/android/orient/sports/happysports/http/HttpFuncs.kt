package com.android.orient.sports.happysports.http

import com.android.orient.sports.happysports.entity.LoginRequest
import com.android.orient.sports.happysports.entity.NetWorkRequest
import com.android.orient.sports.happysports.entity.StepRequest
import com.android.orient.sports.happysports.utils.accessToken
import com.android.orient.sports.happysports.utils.authToken
import com.android.orient.sports.happysports.utils.vhsToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * @PackageName com.android.orient.sports.happysports.http
 * @date 2019/1/24 14:12
 * @author zhanglei
 */
/**
 * 登录
 */
fun login(userName: String, password: String, success: (response: String) -> Unit = {}, failure: (e: Exception) -> Unit = {}) {
    val request = LoginRequest(userName, password)
    request(request, success, failure)
}

/**
 * 同步步数
 */
fun syncStep(step: String, success: (response: String) -> Unit = {}, failure: (e: Exception) -> Unit = {}) {
    val request = StepRequest(step, vhsToken)
    request(request, success, failure)
}


fun request(request: NetWorkRequest, success: (response: String) -> Unit = {}, failure: (e: Exception) -> Unit = {}) {
    if (authToken.isNotBlank()) {
        request.addHeader("auth_token", authToken)
    }
    if (accessToken.isNotBlank()) {
        request.addHeader("access_token", accessToken)
        request.addHeader("access_token", accessToken)
    }
    HttpClients.client.newCall(request.getRequest())
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    failure.invoke(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        success.invoke(response.body()?.string() ?: "")
                    } finally {
                        response.body()?.close()
                    }
                }
            })

}