package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType

class BreakSession(var timerPreference: TimerPreference) : Session() {

    override fun getDurationInSeconds(): Int {
        return timerPreference.breakDurationInSec
    }

    override fun setNextSession() {
        timerPreference.sessionType = SessionType.WORK
    }

    override fun prepNextSession() {
        super.prepNextSession()
        setSessionCounter()
    }

    private fun setSessionCounter() {
        timerPreference.breakSessionCounter++
    }
}