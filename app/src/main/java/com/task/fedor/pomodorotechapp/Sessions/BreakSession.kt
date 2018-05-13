package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class BreakSession(var timerPreference: TimerPreference) : Session {

    override fun getDurationInSeconds(): Int {
        return timerPreference.breakDurationInSec
    }

    override fun onFinish() {
        timerPreference.breakSessionCounter
        timerPreference.sessionType = SessionType.WORK.name
    }
}