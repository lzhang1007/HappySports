package com.android.orient.sports.happysports.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.orient.practice.kldf.kldf.util.CacheUtil
import com.android.orient.sports.happysports.R
import com.android.orient.sports.happysports.alarm.AlarmService
import com.android.orient.sports.happysports.alarm.AlarmService.*
import com.android.orient.sports.happysports.utils.startAlarmService
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)
        timePicker.setIs24HourView(true)
        timePicker.currentHour = CacheUtil.getAppShared().getInt("SET_HOURS", 0)
        timePicker.currentMinute = CacheUtil.getAppShared().getInt("SET_MINUTES", 0)
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectTime.text = "您选择的同步时间为每天的$hourOfDay:$minute"
        }
        switchBtn.isChecked = CacheUtil.getAppShared().getBoolean("TimePickerState", false)
        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                CacheUtil.putAppShared("SET_HOURS", timePicker.currentHour)
                CacheUtil.putAppShared("SET_MINUTES", timePicker.currentMinute)
                startAlarmService(this@AlarmSettingActivity)
            } else {
                val intent = Intent(this@AlarmSettingActivity, AlarmService::class.java)
                intent.putExtra(KEY_ACTION, 1)
                startService(intent)
            }
            CacheUtil.putAppShared("TimePickerState", isChecked)
        }
        selectTime.text = "您选择的同步时间为每天的${timePicker.currentHour}:${timePicker.currentMinute}"
    }
}
