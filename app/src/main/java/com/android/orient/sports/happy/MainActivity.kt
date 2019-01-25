package com.android.orient.sports.happy

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happy.activity.AlarmSettingActivity
import com.android.orient.sports.happy.activity.LoginActivity
import com.android.orient.sports.happy.entity.StepResponse
import com.android.orient.sports.happy.http.syncStep
import com.android.orient.sports.happy.utils.accessToken
import com.android.orient.sports.happy.utils.authToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_REQUEST_CODE = 1000
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
            showProgress(true)
            syncStep(my_steps.text.toString().toInt(), StepResponse::class.java, success = {
                setupViewData()
                logMessage("同步数据成功")
                showProgress(false)
            }, failure = {
                logMessage("同步数据失败\n$it")
                showProgress(false)
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
        my_info.text = "access_token = $accessToken " +
                "\n authToken = $authToken"
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
