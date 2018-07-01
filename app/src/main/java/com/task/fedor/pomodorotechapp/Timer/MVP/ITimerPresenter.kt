package com.task.fedor.pomodorotechapp.Timer.MVP

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.CustomTimer

interface ITimerPresenter {

    fun initTimer(timerPreference: TimerPreference)
    fun startTimer()
    fun stopTimer()
    fun pauseTimer()
    fun skipTimer()

    fun updateView()
    fun showStopAlertDialog()
    fun hideAlertDialogs()
    fun releaseAlertMedia()
}