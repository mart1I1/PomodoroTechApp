package com.task.fedor.pomodorotechapp.Timer.MVP

import com.arellomobile.mvp.MvpView
import com.task.fedor.pomodorotechapp.SessionType
import com.task.fedor.pomodorotechapp.TimerState

interface TimerView : MvpView {

    fun setTimerMax(max : Int)
    fun setTimerProgress(seconds : Int)

    fun updateBtn(state: TimerState)

    fun showStopAlertDialog()
    fun showFinishAlertDialog()
    fun hideAlertDialogs()

    fun runFinishAlert()

    fun releaseAlertMedia()

    fun initSegmentedProgressBarSession()
    fun setSegmentedProgress(progress : Int)
}