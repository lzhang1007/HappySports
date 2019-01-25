package com.android.orient.sports.happy.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.orient.sports.happy.R
import com.android.orient.sports.happy.alarm.AlarmService
import com.android.orient.sports.happy.alarm.AlarmService.KEY_ACTION
import com.android.orient.sports.happy.utils.alarmHour
import com.android.orient.sports.happy.utils.alarmMinute
import com.android.orient.sports.happy.utils.alarmOn
import com.android.orient.sports.happy.utils.startAlarmService
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)
        timePicker.setIs24HourView(true)
        timePicker.currentHour = alarmHour
        timePicker.currentMinute = alarmMinute
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectTime.text = "您选择的同步时间为每天的$hourOfDay:$minute"
        }
        switchBtn.isChecked = alarmOn
        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmHour = timePicker.currentHour
                alarmMinute = timePicker.currentMinute
                startAlarmService(this@AlarmSettingActivity)
            } else {
                val intent = Intent(this@AlarmSettingActivity, AlarmService::class.java)
                intent.putExtra(KEY_ACTION, 1)
                startService(intent)
            }
            alarmOn = isChecked
        }
        selectTime.text = "您选择的同步时间为每天的${timePicker.currentHour}:${timePicker.currentMinute}"
    }
}
