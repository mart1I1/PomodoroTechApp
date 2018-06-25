package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType

class LongBreakSession(var timerPreference: TimerPreference) : Session() {

    override fun getDurationInSeconds(): Int {
        return timerPreference.longBreakDurationInSec
    }

    override fun setNextSession() {
        timerPreference.sessionType = SessionType.WORK
    }

    override fun prepNextSession() {
        super.prepNextSession()
        setSessionCounter()
    }

    private fun setSessionCounter() {
        timerPreference.breakSessionCounter = 0
    }
}