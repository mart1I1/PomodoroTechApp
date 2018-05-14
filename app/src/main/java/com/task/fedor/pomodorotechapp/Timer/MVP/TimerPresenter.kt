package com.task.fedor.pomodorotechapp.Timer.MVP

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.task.fedor.pomodorotechapp.Timer.CustomTimer
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.Sessions.*
import com.task.fedor.pomodorotechapp.Timer.TimerNotification

@InjectViewState
class TimerPresenter : MvpPresenter<TimerView>() {
    val TAG = "TimerPresenter"

    lateinit var timerPreference: TimerPreference
    lateinit var timer : CustomTimer
        private set

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        timerInit()
        updateView()
    }

    private fun timerInit() {
        val session = sessionFactory()
        val timerListener = object : CustomTimer.TimerListener {
            override fun onFinish() {
                deleteInstance()
                session.onFinish()
                timerInit()
                viewState.showFinishAlert()
            }

            override fun onTick(secondsRemaining: Int) {
                viewState.updateTimerProgress(secondsRemaining)
                timerPreference.progressInSeconds = secondsRemaining
            }
        }
        timer = if (timerPreference.emergencyStop){
            CustomTimer(
                    session.getDurationInSeconds(),
                    timerListener,
                    timerPreference.progressInSeconds,
                    TimerState.PAUSED)

        } else {
            CustomTimer(session.getDurationInSeconds(), timerListener)
        }
        deleteInstance()
    }

    fun startTimer(){
        timer.start()
        updateView()
    }

    fun stopTimer(){
        timer.stop()
        updateView()
    }

    fun pauseTimer(){
        timer.pause()
        updateView()
    }

    private fun sessionFactory(): Session {
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
                throw IllegalArgumentException("session not found")
            }
        }
    }

    fun updateView() {
        viewState.updateUIBtn(timer.state)
        viewState.setTimerMax(timer.durationInSec)
        when(timer.state) {
            TimerState.STOPPED -> viewState.updateTimerProgress(timer.durationInSec)
            TimerState.PAUSED -> viewState.updateTimerProgress(timer.secondsRemaining)
        }
    }

    fun stopAlert(){
        viewState.showStopAlert()
    }

    fun hideAlertDialog(){
        viewState.hideAlertDialog()
    }

    fun createNotification(context: Context) {
        if (timer.state == TimerState.RUNNING)
            TimerNotification.create(timer.secondsRemaining, context)
    }

    fun deleteNotification(context: Context) {
        TimerNotification.delete(context)
    }

    fun saveInstanceOnStop() {
        if (timer.state != TimerState.STOPPED) {
            timerPreference.emergencyStop = true
        }
    }

    fun deleteInstance(){
        timerPreference.emergencyStop = false
    }
}