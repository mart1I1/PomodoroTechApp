package com.task.fedor.pomodorotechapp.Sessions

import android.util.Log
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class BreakSession(var timerPreference: TimerPreference) : Session {

    override fun getDurationInSeconds(): Int {
        return 60
    }

    override fun onFinish() {
        timerPreference.breakSessionCount
        timerPreference.sessionType = SessionType.WORK.name
    }
}