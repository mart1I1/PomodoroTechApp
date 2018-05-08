package com.task.fedor.pomodorotechapp.MVP.Timer

import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.Sessions.*

@InjectViewState
class TimerPresenter : MvpPresenter<TimerView>() {

    val countDownInterval : Long = 500

    lateinit var timerPreference: TimerPreference
    lateinit var countDownTimer: CountDownTimer
    lateinit var session: Session
    var state = TimerState.STOPPED

    fun startTimer(){
        val duration = getCurrDurationInSec(timerPreference)

        state = TimerState.RUNNING
        viewState.updateUIBtn(state)

        countDownTimer = object : CountDownTimer(
                (duration * 1000).toLong(),
                countDownInterval) {
            override fun onTick(leftTimeInMilliseconds: Long) {
                val seconds = leftTimeInMilliseconds / 1000
                viewState.updateTimerProgress(seconds.toInt())
                timerPreference.progressInSeconds = seconds.toInt()
            }

            override fun onFinish() {
                state = TimerState.STOPPED
                session.onFinish()

                viewState.onTimerFinish()
            }
        }.start()
    }

    fun stopTimer(){
        state = TimerState.STOPPED
        countDownTimer.cancel()

        viewState.updateTimerProgress(session.getDurationInSeconds())
        viewState.updateUIBtn(state)
    }

    fun pauseTimer(){
        state = TimerState.PAUSED
        countDownTimer.cancel()

        viewState.updateUIBtn(state)
    }

    private fun sessionFactory(timerPreference: TimerPreference): Session {
        return when (timerPreference.sessionType){
            SessionType.WORK.name -> {
                WorkSession(timerPreference)
            }
            SessionType.BREAK.name -> {
                BreakSession(timerPreference)
            }
            SessionType.LONG_BREAK.name -> {
                LongBreakSession(timerPreference)
            }
            else -> {
                throw Exception()
            }
        }
    }

    private fun getCurrDurationInSec(timerPreference: TimerPreference) : Int {
        return if (state == TimerState.PAUSED) {
            timerPreference.progressInSeconds
        }
        else {
            session.getDurationInSeconds()
        }
    }

    fun initPresenterAndView(timerPreference: TimerPreference){
        initPresenterFields(timerPreference)
        initView(timerPreference)
    }

    private fun initPresenterFields(timerPreference: TimerPreference){
        this.timerPreference = timerPreference
        session = sessionFactory(timerPreference)
    }

    private fun initView(timerPreference: TimerPreference){
        viewState.setTimerMax(session.getDurationInSeconds())
        viewState.updateUIBtn(state)
        when (state){
            TimerState.STOPPED -> {
                viewState.updateTimerProgress(session.getDurationInSeconds())
            }
            else -> {
                viewState.updateTimerProgress(timerPreference.progressInSeconds)
            }
        }
    }
}