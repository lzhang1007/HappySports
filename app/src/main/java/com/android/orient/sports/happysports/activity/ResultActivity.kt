package com.android.orient.sports.happysports.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happysports.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        /*val lastSteps = CacheUtil.getAppShared().getInt("last_steps", 0)
        val account = CacheUtil.getAccount()
        val lastTime = CacheUtil.getAppShared().getString("token_date", "" + System.currentTimeMillis())
        result.text = ("当前登录的账号为：" + "\n" + account
                + "\n" + "最后同步日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", java.lang.Long.parseLong(lastTime)) + ", 步数为:$lastSteps"
                + "\n" + "当前版本号为：" + CacheUtil.getAppShared().getString("app_version", "--")
                + "\n")
        result2.text = CacheUtil.getAppShared().getString("receiver_update", "--")*/
    }
}
