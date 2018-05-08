package com.task.fedor.pomodorotechapp.Preferences

import android.content.Context
import com.task.fedor.pomodorotechapp.Sessions.SessionType

class TimerPreference(context : Context) {

    private val PREFERENCE_NAME = "TIMER"
    private val PREFERENCE_SESSION_TYPE = "SESSION_TYPE"
    private val PREFERENCE_TIMER_PROGRESS = "TIMER_PROGRESS"
    private val PREFERENCE_BREAK_SESSION_COUNT = "BREAK_SESSION_COUNT"

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

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

    var breakSessionCount : Int
        get() = preference.getInt(PREFERENCE_BREAK_SESSION_COUNT, 0)
        set(name) {
            val editor = preference.edit()
            editor.putInt(PREFERENCE_BREAK_SESSION_COUNT, name)
            editor.apply()
        }
}