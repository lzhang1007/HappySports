package com.android.orient.sports.happysports.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import com.android.orient.practice.kldf.kldf.util.CacheUtil
import com.android.orient.sports.happysports.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val account = CacheUtil.getAccount()
        val lastTime = CacheUtil.getAppShared().getString("token_date", "" + System.currentTimeMillis())
        result.text = ("当前登录的账号为：" + "\n" + account
                + "\n" + "最后同步日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", java.lang.Long.parseLong(lastTime))
                + "\n" + "当前版本号为：" + CacheUtil.getAppShared().getString("app_version", "--")
                + "\n")
        result2.text = CacheUtil.getAppShared().getString("receiver_update", "--")
    }
}
