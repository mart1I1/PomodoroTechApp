package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import com.task.fedor.pomodorotechapp.TimerState

interface CustomTimer {
    fun start()
    fun pause()
    fun stop()
    fun skip()

    fun getSecondsRemaining() : Int
    fun getDurationInSec() : Int
    fun getState() : TimerState
}