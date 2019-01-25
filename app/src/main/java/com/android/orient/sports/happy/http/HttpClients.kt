package com.android.orient.sports.happy.http

import android.annotation.SuppressLint
import com.android.orient.sports.happy.utils.appContext
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * @PackageName com.android.orient.sports.happysports.http
 * @date 2019/1/23 15:05
 * @author zhanglei
 */
object HttpClients {
    val client: OkHttpClient

    init {
        client = setupClient() ?: throw RuntimeException("Https Client init error")
    }

    private fun setupClient(): OkHttpClient? {
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, arrayOf<TrustManager>(MyTrustManager()), SecureRandom())
        val sslSocketFactory = getSSLSocketFactory() ?: sc.socketFactory
        try {
            return OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(MyHostnameVerifier())
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .cookieJar(CustomCookie())
                    .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getSSLSocketFactory(): SSLSocketFactory? {
        val cf = CertificateFactory.getInstance("X.509")
        var caInput: BufferedInputStream? = null

        try {
            val ca = appContext.assets.open("https.cer")
            caInput = BufferedInputStream(ca)
            val ca1: Certificate = cf.generateCertificate(caInput)

            val keyStoreType1 = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType1)
            keyStore.load(null as InputStream?, null as CharArray?)
            keyStore.setCertificateEntry("ca", ca1)
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null as Array<KeyManager>?, tmf.trustManagers, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            caInput?.close()
        }
        return null
    }

    private class MyTrustManager : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

    private class MyHostnameVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }

    }

    private class CustomCookie : CookieJar {
        private val cookieStore = LinkedHashMap<HttpUrl, List<Cookie>>()
        override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>) {
            cookieStore[httpUrl] = list
        }

        override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
            return cookieStore[httpUrl] ?: ArrayList()
        }
    }
}