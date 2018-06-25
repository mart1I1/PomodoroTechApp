package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import android.os.CountDownTimer
import com.task.fedor.pomodorotechapp.TimerState

open class BaseCustomTimer(durationInSec: Int,
                           private val timerListener: TimerListener,
                           startFrom: Int = 0) : CustomTimer {
    var durationInSec = durationInSec
        private set
    private lateinit var countDownTimer : CountDownTimer
    private val countDownInterval : Long = 500
    var secondsRemaining: Int = 0
        private set
    var state = TimerState.STOPPED
        private set

    init {
        if (startFrom > durationInSec)
            throw IllegalArgumentException("startFrom > durationInSec")
        if (startFrom != 0) {
            secondsRemaining = startFrom
            state = TimerState.PAUSED
        }
    }

    interface TimerListener {
        fun onTick(secondsRemaining : Int)
        fun onFinish()
    }

    private fun createCountDownTimer(durationInSec: Int, timerListener: TimerListener) : CountDownTimer{
        return object : CountDownTimer((durationInSec * 1000).toLong(), countDownInterval){
            override fun onFinish() {
                state = TimerState.STOPPED
                this@BaseCustomTimer.onFinish()
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

    protected open fun onFinish() {
        timerListener.onFinish()
    }

    override fun start() {
        if (state == TimerState.STOPPED || state == TimerState.PAUSED) {
            countDownTimer = countdownTimerFactory(state)
            countDownTimer.start()

            state = TimerState.RUNNING
        }
    }

    override fun pause() {
        if (state == TimerState.RUNNING) {
            countDownTimer.cancel()

            state = TimerState.PAUSED
        }
    }

    override fun stop() {
        if (state == TimerState.RUNNING || state == TimerState.PAUSED) {
            if (this::countDownTimer.isInitialized)
                countDownTimer.cancel()
            secondsRemaining = 0

            state = TimerState.STOPPED
        }
    }

    override fun skip() {
        stop()
        this.onFinish()
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
                throw IllegalStateException("wrong state for timer factory")
            }
        }
    }
}