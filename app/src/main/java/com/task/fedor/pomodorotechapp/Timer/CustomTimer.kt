package com.task.fedor.pomodorotechapp.Timer

import android.os.CountDownTimer
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState

class CustomTimer(var durationInMillis: Long, var timerListener: TimerListener) {
    private var countDownTimer : CountDownTimer
    private val countDownInterval : Long = 500
    var millisRemaining: Long = 0
        private set
    var state = TimerState.STOPPED
        private set

    interface TimerListener{
        fun onFinish()
        fun onTick(millisRemaining : Long)
    }

    init {
        countDownTimer = createCountDownTimer(durationInMillis, timerListener)
    }

    private fun createCountDownTimer(durationInMillis: Long, timerListener: TimerListener) : CountDownTimer{
        return object : CountDownTimer(durationInMillis, countDownInterval){
            override fun onFinish() {
                timerListener.onFinish()
                state = TimerState.STOPPED
            }

            override fun onTick(millisRemaining: Long) {
                this@CustomTimer.millisRemaining = millisRemaining
                timerListener.onTick(millisRemaining)
            }
        }
    }

    fun start(){
        if (state == TimerState.STOPPED) {
            countDownTimer = createCountDownTimer(durationInMillis, timerListener)
            countDownTimer.start()
            state = TimerState.RUNNING
        }
    }

    fun pause(){
        countDownTimer.cancel()
        state = TimerState.PAUSED
    }

    fun stop(){
        countDownTimer.cancel()
        millisRemaining = 0
        state = TimerState.STOPPED
    }

    fun resume(){
        if (state == TimerState.PAUSED) {
            countDownTimer = createCountDownTimer(millisRemaining, timerListener)
            countDownTimer.start()
            state = TimerState.RUNNING
        }
        else throw IllegalStateException("not a pause state")
    }

}