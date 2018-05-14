package com.task.fedor.pomodorotechapp.Timer.MVP

import com.arellomobile.mvp.MvpView

interface TimerView : MvpView {

    fun setTimerMax(max : Int)

    fun updateUIBtn(state: TimerState)

    fun updateTimerProgress(seconds : Int)

    fun showStopAlert()

    fun hideAlertDialog()

    fun showFinishAlert()
}