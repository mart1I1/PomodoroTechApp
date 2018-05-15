package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import android.os.CountDownTimer
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState

open class BaseCustomTimer(var durationInSec: Int,
                           val timerListener: TimerListener,
                           startFrom: Int = 0) : CustomTimer {
    private var countDownTimer : CountDownTimer
    private val countDownInterval : Long = 500
    var secondsRemaining: Int = 0
        private set
    var state = TimerState.STOPPED
        private set

    init {
        if (startFrom != 0) {
            secondsRemaining = startFrom
            state = TimerState.PAUSED
        }
        countDownTimer = countdownTimerFactory(state)
    }

    interface TimerListener {
        fun onTick(secondsRemaining : Int)
        fun onFinish()
    }

    private fun createCountDownTimer(durationInSec: Int, timerListener: TimerListener) : CountDownTimer{
        return object : CountDownTimer((durationInSec * 1000).toLong(), countDownInterval){
            override fun onFinish() {
                state = TimerState.STOPPED
                timerListener.onFinish()
            }

            override fun onTick(millisRemaining: Long) {
                secondsRemaining = (millisRemaining / 1000).toInt()
                onTick(secondsRemaining)
            }
        }
    }

    protected open fun onTick(secondsRemaining: Int){
        timerListener.onTick(secondsRemaining)
    }

    override fun start() {
        countDownTimer = countdownTimerFactory(state)
        countDownTimer.start()

        state = TimerState.RUNNING

    }

    override fun pause() {
        countDownTimer.cancel()

        state = TimerState.PAUSED
    }

    override fun stop() {
        countDownTimer.cancel()
        secondsRemaining = 0

        state = TimerState.STOPPED
    }

    private fun countdownTimerFactory(state : TimerState) : CountDownTimer {
        return when (state) {
            TimerState.STOPPED -> {
                createCountDownTimer(durationInSec, timerListener)
            }
            TimerState.PAUSED -> {
                createCountDownTimer(secondsRemaining, timerListener)
            }
            else -> {
                throw IllegalStateException("wrong state")
            }
        }
    }
}