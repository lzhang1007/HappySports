package com.android.orient.sports.happysports.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.orient.sports.happysports.R
import com.android.orient.sports.happysports.alarm.AlarmService
import com.android.orient.sports.happysports.alarm.AlarmService.*
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectTime.text = "您选择的同步时间为每天的$hourOfDay:$minute"
        }
        btnAlarm.setOnClickListener {
            val intent = Intent(this@AlarmSettingActivity, AlarmService::class.java)
            intent.putExtra(KEY_HOUR, timePicker.currentHour)
            intent.putExtra(KEY_MINUTE, timePicker.currentMinute)
            intent.putExtra(KEY_ACTION, 0)
            startService(intent)
        }

        btnCancelAlarm.setOnClickListener {
            val intent = Intent(this@AlarmSettingActivity, AlarmService::class.java)
            intent.putExtra(KEY_ACTION, 1)
            startService(intent)
        }
        selectTime.text = "您选择的同步时间为每天的${timePicker.currentHour}:${timePicker.currentMinute}"
    }
}
