package com.task.fedor.pomodorotechapp.MVP.Timer

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface TimerView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun setTimerMax(max : Int)

    @StateStrategyType(SingleStateStrategy::class)
    fun onTimerFinish()

    @StateStrategyType(SingleStateStrategy::class)
    fun updateUIBtn(state: TimerState)

    @StateStrategyType(SingleStateStrategy::class)
    fun updateTimerProgress(seconds: Int)
}