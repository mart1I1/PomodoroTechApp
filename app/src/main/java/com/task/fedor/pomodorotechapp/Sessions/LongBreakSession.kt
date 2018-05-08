package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class LongBreakSession(var timerPreference: TimerPreference) : Session {
    override fun getDurationInSeconds(): Int {
        return 3 * 60
    }

    override fun onFinish() {
        timerPreference.breakSessionCount = 0
        timerPreference.sessionType = SessionType.WORK.name
    }
}