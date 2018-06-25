package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType

class WorkSession(var timerPreference: TimerPreference) : Session() {

    override fun getDurationInSeconds(): Int {
        return timerPreference.workDurationInSec
    }

    override fun setNextSession() {
        if (timerPreference.breakSessionCounter + 1 == timerPreference.longBreakPeriod)
            timerPreference.sessionType = SessionType.LONG_BREAK
        else
            timerPreference.sessionType = SessionType.BREAK
    }
}