package com.task.fedor.pomodorotechapp.Timer

import android.os.CountDownTimer
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState

class CustomTimer(var durationInSec: Int, var timerListener: TimerListener) {
    private var countDownTimer : CountDownTimer
    private val countDownInterval : Long = 500
    var secondsRemaining: Int = 0
        private set
    var state = TimerState.STOPPED
        private set

    interface TimerListener{
        fun onFinish()
        fun onTick(secondsRemaining : Int)
    }

    init {
        countDownTimer = createCountDownTimer(durationInSec, timerListener)
    }

    constructor(durationInSec: Int,
                timerListener: TimerListener,
                secondsRemaining: Int,
                state: TimerState) : this(durationInSec, timerListener){
        this.secondsRemaining = secondsRemaining
        this.state = state
    }

    private fun createCountDownTimer(durationInSec: Int, timerListener: TimerListener) : CountDownTimer{
        return object : CountDownTimer((durationInSec * 1000).toLong(), countDownInterval){
            override fun onFinish() {
                state = TimerState.STOPPED
                timerListener.onFinish()
            }

            override fun onTick(millisRemaining: Long) {
                secondsRemaining = (millisRemaining / 1000).toInt()
                timerListener.onTick(secondsRemaining)
            }
        }
    }

    fun start(){
        when (state) {
            TimerState.STOPPED -> {
                countDownTimer = createCountDownTimer(durationInSec, timerListener)
            }
            TimerState.PAUSED -> {
                countDownTimer = createCountDownTimer(secondsRemaining, timerListener)
            }
            else -> {
                throw IllegalStateException("wrong state")
            }
        }
        countDownTimer.start()
        state = TimerState.RUNNING
    }

    fun pause(){
        countDownTimer.cancel()
        state = TimerState.PAUSED
    }

    fun stop(){
        countDownTimer.cancel()
        secondsRemaining = 0
        state = TimerState.STOPPED
    }
}