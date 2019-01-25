package com.android.orient.sports.happy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happy.R
import com.android.orient.sports.happy.utils.appVersion
import com.android.orient.sports.happy.utils.userName
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
