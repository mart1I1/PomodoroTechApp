package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class WorkSession(var timerPreference: TimerPreference) : Session {

    override fun getDurationInSeconds(): Int {
        return timerPreference.workDurationInSec
    }

    override fun onFinish() {
        if (timerPreference.breakSessionCounter == timerPreference.longBreakPeriod)
            timerPreference.sessionType = SessionType.LONG_BREAK.name
        else
            timerPreference.sessionType = SessionType.BREAK.name
    }
}