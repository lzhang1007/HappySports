package com.android.orient.sports.happysports.entity

import android.text.TextUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.net.URLEncoder
import java.util.*

/**
 * @PackageName com.android.orient.sports.happysports.entity
 * @date 2019/1/25 13:39
 * @author zhanglei
 */
open class BaseRequest(baseUrl: String, private val bizMethod: String?) {
    val mapHeaders: MutableMap<String, String> = HashMap()
    val mapEntities: MutableMap<String, Any> = HashMap()
    private var sysObj: JSONObject? = null
    var bizObj: JSONObject? = null
    /**
     * 用于获取本次Http请求的code值。
     * @return
     */
    /**
     * 设置本次http请求的Code。
     * @param requestCode
     */
    var requestCode: Int = 0
    /**
     * 获取本次http请求的网络地址。
     * @return
     */
    var requestUrl: String
        protected set
    protected var keyCode: String? = null
        set
    /**
     * 是否需要对请求数据进行URLEncode
     * @return
     */

    var isNeedUrlEncode = false
    /**
     * 是否加密Biz参数
     * @return boolean
     */
    var isBizEncrypted: Boolean = false
        private set
    var title: String? = null
    var webCallBack: Any? = null
    protected var session: String = ""
    var requestParam: String = ""

    /**
     * 获取DeviceType，默认返回“Android”
     * @return String
     */
    protected val deviceType: String
        get() = "android"

    /**
     * 获取AppCode，默认返回“moa”。
     * @return String
     */
    protected val appCode: String
        get() = "moa"

    /**
     * 获取完整的请求参数
     */
    val requestParams: String?
        get() {
            try {
                initSysParams()
                val params = JSONObject()
                params.put("sys", sysObj)
                var biz = ""
                if (bizObj != null) {
                    biz = bizObj!!.toString()
                    /*if (isBizEncrypted) {
                        biz = JQEncrypt.encrypt(biz)
                    }*/
                }
                params.put("biz", biz)
                var retParams = params.toString()
                if (isNeedUrlEncode) {
                    retParams = URLEncoder.encode(retParams, "utf-8")
                }
                return retParams
            } catch (e: Exception) {
            }

            return null
        }

    init {
        if (TextUtils.isEmpty(bizMethod)) {
            this.requestUrl = baseUrl
        } else {
            if (baseUrl.endsWith(File.separator)) {
                this.requestUrl = baseUrl + bizMethod!!
            } else {
                this.requestUrl = baseUrl + File.separator + bizMethod
            }
        }
        keyCode = "jkznf608553555666067505987771900"//if (TextUtils.isEmpty(ServerConfig.getInstance().xmasKey)) "jkznf608553555666067505987771900" else ServerConfig.getInstance().xmasKey
//        isBizEncrypted = if (TextUtils.isEmpty(ServerConfig.getInstance().encryptdata)) true else TextUtils.equals(ServerConfig.getInstance().encryptdata, "true")
    }

    /**
     * 初始化业务参数，一般不需要重载。（已初始化：method,appcode,devicetype,compressdata
     * ,encrypdata,keycode）
     * @return void
     * @throws
     */
    private fun initSysParams() {
        addSysParam("method", bizMethod)
        addSysParam("appcode", appCode)//default "moa"
        addSysParam("devicetype", deviceType)//default "Android"
        addSysParam("compressdata", false)//default "false"
        addSysParam("encryptdata", isBizEncrypted.toString()) //default "true"
        if (null != keyCode)
            addSysParam("keycode", keyCode)//default "null"
    }

    protected fun initEntityMap() {
        if (!mapEntities.containsKey(ENTITY_NAME_XMAS_JSON)) {
            mapEntities[ENTITY_NAME_XMAS_JSON] = requestParams!!
        }
    }

    /**
     * 添加BIZ请求参数
     */
    fun addBizParam(key: String, value: Any) {
        if (null == bizObj) {
            bizObj = JSONObject()
        }
        try {
            bizObj!!.put(key, value)
        } catch (e: JSONException) {
        }

    }

    fun getBizParam(key: String): Any? {
        try {
            return bizObj!!.get(key)
        } catch (e: JSONException) {
        }

        return null
    }


    /**
     * 添加SYS请求参数
     */
    fun addSysParam(key: String, value: Any?) {
        if (null == sysObj) {
            sysObj = JSONObject()
        }
        try {
            sysObj!!.put(key, value)
        } catch (e: JSONException) {
        }

    }

    fun addCustomsEntity(key: String, value: Any): Any? {
        return mapEntities.put(key, value)
    }

    /**
     * 添加调用方自定义的Http请求头部。
     * @param key The key of one header.
     * @param value The value of one header.
     * @return the value of any previous mapping with the specified
     * key or null if there was no mapping.
     */
    fun addCustomsHeader(key: String, value: String): String? {
        return mapHeaders.put(key, value)
    }

    companion object {

        val ENTITY_NAME_XMAS_JSON = "xmas-json"
        val ENTITY_NAME_UP_FILE = "upFile"
    }


}