package com.task.fedor.pomodorotechapp.Preferences

import android.content.Context
import com.task.fedor.pomodorotechapp.Sessions.SessionType
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState

class TimerPreference(context : Context) {

    private val PREFERENCE_NAME = "TIMER"
    private val PREFERENCE_SESSION_TYPE = "SESSION_TYPE"
    private val PREFERENCE_TIMER_PROGRESS = "TIMER_PROGRESS"
    private val PREFERENCE_BREAK_SESSION_COUNT = "BREAK_SESSION_COUNT"
    private val PREFERENCE_WORK_DURATION = "WORK_DURATION"
    private val PREFERENCE_BREAK_DURATION = "BREAK_DURATION"
    private val PREFERENCE_LONG_BREAK_DURATION = "LONG_BREAK_DURATION"
    private val PREFERENCE_LONG_BREAK_PERIOD = "LONG_BREAK_PERIOD"
    private val PREFERENCE_TIMER_STATE = "TIMER_STATE"

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    var state : TimerState
        get() = TimerState.valueOf(preference.getString(PREFERENCE_TIMER_STATE, TimerState.STOPPED.toString()))
        set(state) {
            val editor = preference.edit()
            editor.putString(PREFERENCE_TIMER_STATE, state.name)
            editor.apply()
        }

    var progressInSeconds: Int
        get() = preference.getInt(PREFERENCE_TIMER_PROGRESS, 0)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_TIMER_PROGRESS, value)
            editor.apply()
        }

    var sessionType: String
        get() = preference.getString(PREFERENCE_SESSION_TYPE, SessionType.WORK.name)
        set(name) {
            val editor = preference.edit()
            editor.putString(PREFERENCE_SESSION_TYPE, name)
            editor.apply()
        }

    var breakSessionCounter : Int
        get() = preference.getInt(PREFERENCE_BREAK_SESSION_COUNT, 0)
        set(name) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_BREAK_SESSION_COUNT, name)
            editor.apply()
        }

    var workDurationInSec : Int
        get() = preference.getInt(PREFERENCE_WORK_DURATION, 2 * 60)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_WORK_DURATION, value)
            editor.apply()
        }

    var breakDurationInSec : Int
        get() = preference.getInt(PREFERENCE_BREAK_DURATION, 1 * 60)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_BREAK_DURATION, value)
            editor.apply()
        }

    var longBreakDurationInSec : Int
        get() = preference.getInt(PREFERENCE_LONG_BREAK_DURATION, 3 * 60)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_LONG_BREAK_DURATION, value)
            editor.apply()
        }

    var longBreakPeriod : Int
        get() = preference.getInt(PREFERENCE_LONG_BREAK_PERIOD, 4)
        set(value) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_LONG_BREAK_PERIOD, value)
            editor.apply()
        }

}