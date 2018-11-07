package com.android.orient.practice.kldf.kldf.util

import android.os.Build
import com.android.orient.practice.kldf.kldf.constant.Constant
import com.android.orient.practice.kldf.kldf.constant.HttpConstant
import com.encrypt.EncryptUtil
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.HashMap

/**
 * Package Name: com.android.orient.practice.kldf.kldf.util
 * Date: 2018/11/6 16:51
 * Author: zhanglei
 */
private val pSymKey = ByteArray(16)
private val client = OkHttpClient()
private var token: String
    get() = CacheUtil.getToken()
    set(value) = CacheUtil.setToken(value)

private val cacheUrl: HashMap<String, Boolean> by lazy {
    val map = hashMapOf<String, Boolean>()
    map[HttpConstant.GET_INDEXBANNER] = true
    map[HttpConstant.GET_INDEXDYNAMIC] = true
    map[HttpConstant.GET_DISCOVER] = true
    map[HttpConstant.GET_MEMBERDETAIL] = true
    map[HttpConstant.GET_MEMBERSCORE] = true
    map[HttpConstant.GET_ICON] = true
    map[HttpConstant.GET_NAVIGATION] = true
    return@lazy map
}

@Throws(IOException::class)
fun post(url: String, json: Any): JSONObject {
    val request = Request.Builder()
            .url(url)
            .headers(getHeaders())
            .post(getBody(Gson().toJson(json)))
            .build()
    val response = client.newCall(request).execute()
    if (response.code() != 200) {
        return JSONObject("{result:\"0099\", info:\"" + Constant.NETWORK_BAD + "\"}")
    }
    val body = AESUtil.decrypt(JSONObject(response.body()!!.string()).getString("data"), String(pSymKey))
    val bodyJson = JSONObject(body)
    parseToken(response, bodyJson)
    cacheResponse(url, bodyJson.toString())
    return bodyJson
}

private fun getHeaders(): Headers {
    val builder = Headers.Builder()
            .add("encrypt", "android")
            .add("osversion", "Android" + Build.VERSION.RELEASE)
            .add("model", Build.MODEL)
            .add("imei", DeviceInfoUtil.getIMEI())
            .add("appversion", DeviceInfoUtil.getVersion())
    if (token.isNotBlank()) {
        builder.add(HttpConstant.TOKEN, token)
    }
    return builder.build()
}

private fun getBody(json: String): RequestBody {
    return FormBody.Builder(Charset.forName("utf-8"))
            .add("key", EncryptUtil().generatedKey(pSymKey))
            .add("data", AESUtil.encrypt(json, String(pSymKey)))
            .build()
}

private fun parseToken(response: Response, bodyJson: JSONObject) {
    val tokenS = response.header(HttpConstant.TOKEN)
    tokenS?.let { token = it }
    val bodyToken = bodyJson.getString(HttpConstant.TOKEN)
    if (bodyToken.isNotBlank() || token.isBlank()) {
        token = bodyToken
    }
}

private fun cacheResponse(url: String, jsonString: String) {
    var jsonObject: JSONObject? = null
    try {
        jsonObject = JSONObject(jsonString)
        if (HttpConstant.SUCCESS == jsonObject.getString("result") && cacheUrl.containsKey(url)) {
            if (HttpConstant.GET_INDEXBANNER == url) {
                CacheUtil.setIndexBanner(jsonString)
            }
            if (HttpConstant.GET_INDEXDYNAMIC == url && jsonObject.has("dyList")) {
                val bannerList = jsonObject.getJSONArray("dyList")
                if (!(bannerList == null || bannerList.length() == 0)) {
                    CacheUtil.setIndexDynamic(jsonString)
                }
            }
            if (HttpConstant.GET_DISCOVER == url) {
                CacheUtil.setDiscover(jsonString)
            }
            if (HttpConstant.GET_MEMBERDETAIL == url) {
                CacheUtil.setMemberDetail(jsonString)
            }
            if (HttpConstant.GET_MEMBERSCORE == url) {
                CacheUtil.setMemberScore(jsonString)
            }
            if (HttpConstant.GET_ICON == url) {
                CacheUtil.setIcon(jsonString)
            }
            if (HttpConstant.GET_NAVIGATION == url) {
                CacheUtil.setNavigation(jsonString)
            }
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }

}