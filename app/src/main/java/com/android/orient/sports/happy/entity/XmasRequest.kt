package com.android.orient.sports.happy.entity

import android.text.TextUtils
import okhttp3.*
import java.io.File

/**
 * @PackageName com.android.orient.sports.happysports.entity
 * @date 2019/1/25 13:40
 * @author zhanglei
 */
class XmasRequest(url: String, method: String) : BaseRequest(url, method) {

    val okHttpRequest: Request?
        get() {
            try {
                if (!TextUtils.isEmpty(session)) {
                    addCustomsHeader("xmas-session", session)
                }
                val requstBuiler = Request.Builder()
                for (entry in mapHeaders.entries) {
                    requstBuiler.addHeader(entry.key, entry.value)
                }

                requstBuiler.url(requestUrl)

                if (mapEntities.size == 1) {
                    val builder = FormBody.Builder()
                    for (entry in mapEntities.entries) {
                        builder.add(entry.key, entry.value as String)
                    }
                    requstBuiler.post(builder.build())
                } else {
                    initEntityMap()
                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    for (entry in mapEntities.entries) {
                        val value = entry.value
                        if (value is String) {
                            val text = entry.value as String
                            builder.addFormDataPart(entry.key, text)
                        } else if (value is Array<*>) {
                            val key = entry.key
                            val upfiles = value as Array<File>
                            for (file in upfiles) {
                                val fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
                                builder.addFormDataPart(key, file.name, fileBody)
                            }
                        }
                    }
                    requstBuiler.post(builder.build())
                }
                requstBuiler.tag(this@XmasRequest::class.java.simpleName)
                requstBuiler.build()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }
}