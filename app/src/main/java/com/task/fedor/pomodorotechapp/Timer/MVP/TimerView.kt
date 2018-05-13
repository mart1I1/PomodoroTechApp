package com.task.fedor.pomodorotechapp.Timer.MVP

import com.arellomobile.mvp.MvpView

interface TimerView : MvpView {

    fun setTimerMax(max : Long)

    fun updateUIBtn(state: TimerState)

    fun updateTimerProgress(millis: Long)

    fun showStopAlert()

    fun hideAlertDialog()

    fun showEndAlert()
}