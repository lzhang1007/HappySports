package com.android.orient.sports.happysports.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happysports.R
import com.android.orient.sports.happysports.utils.appVersion
import com.android.orient.sports.happysports.utils.userName
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        result.text = ("当前登录的账号为：" + "\n" + userName
                + "\n" + "当前版本号为：" + appVersion
                + "\n")
    }
}
