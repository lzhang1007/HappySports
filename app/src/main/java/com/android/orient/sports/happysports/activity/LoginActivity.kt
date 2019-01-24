package com.android.orient.sports.happysports.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happysports.R
import com.android.orient.sports.happysports.http.login
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
            login(email, password, success = { response ->
                Log.d("zhanglei", "response = $response")
            })
            /*DataUpdateUtil.sendLoginService(email, password, version, object : ServiceCallBack {

                override fun onStart() {
                    showProgress(true)
                }

                override fun onEnd() {
                    showProgress(false)
                }

                @SuppressLint("SetTextI18n")
                override fun onSuccess(jSONObject: JSONObject) {
                    Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_LONG).show()
                    setResult(Activity.RESULT_OK)
                    this@LoginActivity.finish()
                }

                override fun onFailed(message: String) {
                    my_message.text = ("登录失败\n$message")
                }
            })*/
        }
        setupViewData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewData() {
        /* val account = CacheUtil.getAccount()
         my_version.setText(getAppShared().getString("app_version", "1.42"))
         my_email.setText(account)
         my_password.setText(getAppShared().getString("password", ""))
         val lastTime = CacheUtil.getAppShared().getString("token_date", "0")
         my_message.text = ("当前登录的账号为：" + "\n" + account
                 + "\n" + "最后同步日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", lastTime.toLong())
                 + "\n" + "当前版本号为：" + CacheUtil.getAppShared().getString("app_version", "--"))*/
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
