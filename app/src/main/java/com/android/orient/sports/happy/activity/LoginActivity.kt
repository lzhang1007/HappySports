package com.android.orient.sports.happy.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happy.R
import com.android.orient.sports.happy.entity.LoginResponse
import com.android.orient.sports.happy.http.login
import com.android.orient.sports.happy.utils.appVersion
import com.android.orient.sports.happy.utils.loginDate
import com.android.orient.sports.happy.utils.password
import com.android.orient.sports.happy.utils.userName
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        my_email_sign_in_button.setOnClickListener {
            val email = my_email.text.toString()
            val password = my_password.text.toString()
            val version = my_version.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(version)) {
                Toast.makeText(this@LoginActivity, "账号、密码、版本号不能为空", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            showProgress(true)
            login(email, password, LoginResponse::class.java,
                    success = {
                        userName = email
                        com.android.orient.sports.happy.utils.password = password
                        com.android.orient.sports.happy.utils.appVersion = appVersion
                        loginDate = System.currentTimeMillis().toString()
                        showProgress(false)
                        Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_LONG).show()
                        setResult(Activity.RESULT_OK)
                        this@LoginActivity.finish()
                    },
                    failure = {
                        my_message.text = ("登录失败\n$it")
                        showProgress(false)
                    })
        }
        setupViewData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewData() {
        my_version.setText(appVersion)
        my_email.setText(userName)
        my_password.setText(password)
        my_message.text = ("当前登录的账号为：" + "\n" + userName
                + "\n" + "最后同步日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", loginDate.toLong())
                + "\n" + "当前版本号为：" + appVersion)
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                login_form.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                login_progress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

}
