package com.android.orient.sports.happysports

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import com.android.orient.practice.kldf.base.ServiceCallBack
import com.android.orient.practice.kldf.kldf.util.CacheUtil
import com.android.orient.sports.happysports.activity.AlarmSettingActivity
import com.android.orient.sports.happysports.activity.LoginActivity
import com.android.orient.sports.happysports.utils.DataUpdateUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        val KEY_REQUEST_CODE = 1000
    }

    private val infoBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service.setOnClickListener { startActivity(Intent(this@MainActivity, AlarmSettingActivity::class.java)) }
        login.setOnClickListener { startActivityForResult(Intent(this@MainActivity, LoginActivity::class.java), KEY_REQUEST_CODE) }
        my_update.setOnClickListener {
            if (TextUtils.isEmpty(my_steps.text)) {
                return@setOnClickListener
            }
            DataUpdateUtil.sendStepService(my_steps.text.toString().toInt(), object : ServiceCallBack {
                override fun onStart() {
                    showProgress(true)
                }

                override fun onEnd() {
                    showProgress(false)
                }

                override fun onSuccess(jSONObject: JSONObject) {
                    setupViewData()
                    logMessage("同步数据成功" + "\n" + jSONObject)
                }

                override fun onFailed(message: String) {
                    logMessage("同步数据失败" + "\n" + message)
                }
            })
        }
        setupViewData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                KEY_REQUEST_CODE -> setupViewData()
            }
        }
    }

    private fun logMessage(message: CharSequence) {
        infoBuilder.append(DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis())).append("  ").append(message).append("\n")
        my_info.text = infoBuilder
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewData() {
        val token = CacheUtil.getToken()
        if (TextUtils.isEmpty(token))
            my_message.text = "未登录, 请先登录"
        else {
            val account = CacheUtil.getAccount()
            val lastTime = CacheUtil.getAppShared().getString("token_date", "" + System.currentTimeMillis())
            my_message.text = ("当前登录的账号为：" + "\n" + account
                    + "\n" + "最后同步日期为：" + "\n" + DateFormat.format("yyyy-MM-dd HH:mm:ss", java.lang.Long.parseLong(lastTime))
                    + "\n" + "当前版本号为：" + CacheUtil.getAppShared().getString("app_version", "--"))
        }

        my_info.text = CacheUtil.getAppShared().getString("token_Update_time", "")
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
