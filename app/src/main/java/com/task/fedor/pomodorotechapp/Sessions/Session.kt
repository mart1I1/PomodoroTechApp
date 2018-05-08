package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

interface Session {
    fun getDurationInSeconds() : Int
    fun onFinish()
}