package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class WorkSession(var timerPreference: TimerPreference) : Session {

    override fun getDurationInSeconds(): Int {
        return 2 * 60
    }

    override fun onFinish() {
        if (timerPreference.breakSessionCount == 4)
            timerPreference.sessionType = SessionType.LONG_BREAK.name
        else
            timerPreference.sessionType = SessionType.BREAK.name
    }
}