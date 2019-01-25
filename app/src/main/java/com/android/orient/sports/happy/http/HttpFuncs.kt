package com.android.orient.sports.happy.http

import android.util.Log
import com.android.orient.sports.happy.entity.*
import com.android.orient.sports.happy.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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
fun <T : BaseResponse> login(userName: String, password: String, cls: Class<T>, success: (cls: T) -> Unit = {}, failure: (e: String) -> Unit = {}) {
    val request = LoginRequest(userName, password)
    request(request, cls, success, failure)
}

/**
 * 同步步数
 */
fun <T : BaseResponse> syncStep(step: Int = generateRandomStep(), cls: Class<T>, success: (cls: T) -> Unit = {}, failure: (e: String) -> Unit = {}) {
    val request = StepRequest(step, vhsToken)
    request(request, cls, success, failure)
}

/**
 * 登录并同步数据
 */
fun loginAndSyncStep(step: Int = generateRandomStep(), success: () -> Unit = {}, failure: (e: String) -> Unit = {}) {
    if (userName.isBlank() || password.isBlank()) return
    login(userName, password, LoginResponse::class.java, success = {
        syncStep(step, cls = StepResponse::class.java, success = { success.invoke() }, failure = failure::invoke)
    }, failure = failure::invoke)
}

private fun generateRandomStep(): Int {
    val random = java.util.Random()
    return random.nextInt(10000) + 10000
}

fun <T : BaseResponse> request(request: NetWorkRequest, cls: Class<T>, success: (cls: T) -> Unit = {}, failure: (e: String) -> Unit = {}) {
    Observable.create<BaseResponse> { emitter ->
        if (authToken.isNotBlank()) {
            request.addHeader("auth_token", authToken)
        }
        if (accessToken.isNotBlank()) {
            request.addHeader("access_token", accessToken)
        }
        HttpClients.client.newCall(request.getRequest())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        emitter.onNext(BaseResponse("-1", e.message.toString()))
                        emitter.onComplete()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                            val responseString = response.body()?.string() ?: ""
                            val base = gson.fromJson(responseString, BaseResponse::class.java)
                            if (base.code == HTTP_SUCCESS) {
                                val auth = response.headers().get("auth_token")
                                if (!auth.isNullOrBlank()) {
                                    authToken = auth
                                }
                                val access = response.headers().get("access_token")
                                if (!access.isNullOrBlank()) {
                                    accessToken = access
                                }
                            }
                            emitter.onNext(base)
                        } finally {
                            response.body()?.close()
                            emitter.onComplete()
                        }
                    }
                })
    }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { response ->
                Log.d("zhanglei", response.toString())
                when {
                    response.code == HTTP_SUCCESS -> success.invoke(gson.fromJson(response.data, cls))
                    response.result == HTTP_BAD_TOKEN -> {

                    }
                    else -> {
                        val message = if (response.message.isBlank()) response.info else response.message
                        failure.invoke(message)
                    }
                }
            })

}

private const val HTTP_SUCCESS = "1000"
private const val HTTP_BAD_TOKEN = "4000"