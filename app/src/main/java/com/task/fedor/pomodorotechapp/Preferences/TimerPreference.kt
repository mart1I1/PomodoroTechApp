package com.task.fedor.pomodorotechapp.Preferences

import android.content.Context
import com.task.fedor.pomodorotechapp.SessionType
import com.task.fedor.pomodorotechapp.TimerState

class TimerPreference(var context : Context) {

    private val PREFERENCE_NAME = "TIMER"
    private val PREFERENCE_SESSION_TYPE = "SESSION_TYPE"
    private val PREFERENCE_TIMER_REMAINING = "TIMER_REMAINING"
    private val PREFERENCE_BREAK_SESSION_COUNT = "BREAK_SESSION_COUNT"
    private val PREFERENCE_WORK_DURATION = "WORK_DURATION"
    private val PREFERENCE_BREAK_DURATION = "BREAK_DURATION"
    private val PREFERENCE_LONG_BREAK_DURATION = "LONG_BREAK_DURATION"
    private val PREFERENCE_LONG_BREAK_PERIOD = "LONG_BREAK_PERIOD"
    private val PREFERENCE_TIMER_STATE = "TIMER_STATE"
    private val PREFERENCE_ALARM_MELODY = "ALARM_MELODY"

    val defaultState = TimerState.STOPPED
    val defaultSessionType = SessionType.WORK
    val defaultSecondsRemaining = 0
    val defaultBreakSessionCounter = 0
    val defaultWorkDurationInSec = 2 * 60
    val defaultBreakDurationInSec = 60
    val defaultLongBreakDurationInSec = 3 * 60
    val defaultLongBreakPeriod = 4
    val defaultAlarmMelody = "alarm"

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    var state : TimerState
        get() = TimerState.valueOf(preference.getString(PREFERENCE_TIMER_STATE, defaultState.name))
        set(state) {
            val editor = preference.edit()
            editor.putString(PREFERENCE_TIMER_STATE, state.name)
            editor.apply()
        }

    var secondsRemaining: Int
        get() = preference.getInt(PREFERENCE_TIMER_REMAINING, defaultSecondsRemaining)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_TIMER_REMAINING, value)
            editor.apply()
        }

    var sessionType: SessionType
        get() = SessionType.valueOf(preference.getString(PREFERENCE_SESSION_TYPE, defaultSessionType.name))
        set(type) {
            val editor = preference.edit()
            editor.putString(PREFERENCE_SESSION_TYPE, type.name)
            editor.apply()
        }

    var breakSessionCounter : Int
        get() = preference.getInt(PREFERENCE_BREAK_SESSION_COUNT, defaultBreakSessionCounter)
        set(name) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_BREAK_SESSION_COUNT, name)
            editor.apply()
        }

    var workDurationInSec : Int
        get() = preference.getInt(PREFERENCE_WORK_DURATION, defaultWorkDurationInSec)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_WORK_DURATION, value)
            editor.apply()
        }

    var breakDurationInSec : Int
        get() = preference.getInt(PREFERENCE_BREAK_DURATION, defaultBreakDurationInSec)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_BREAK_DURATION, value)
            editor.apply()
        }

    var longBreakDurationInSec : Int
        get() = preference.getInt(PREFERENCE_LONG_BREAK_DURATION, defaultLongBreakDurationInSec)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_LONG_BREAK_DURATION, value)
            editor.apply()
        }

    var longBreakPeriod : Int
        get() = preference.getInt(PREFERENCE_LONG_BREAK_PERIOD, defaultLongBreakPeriod)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_LONG_BREAK_PERIOD, value)
            editor.apply()
        }

    var alarmMelody : String
        get() = preference.getString(PREFERENCE_ALARM_MELODY, defaultAlarmMelody)
        set(value) {
            val editor = preference.edit()
            editor.putString(PREFERENCE_ALARM_MELODY, value)
            editor.apply()
        }

}