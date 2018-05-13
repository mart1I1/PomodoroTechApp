package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class LongBreakSession(var timerPreference: TimerPreference) : Session {
    override fun getDurationInSeconds(): Int {
        return timerPreference.longBreakDurationInSec
    }

    override fun onFinish() {
        timerPreference.breakSessionCounter = 0
        timerPreference.sessionType = SessionType.WORK.name
    }
}