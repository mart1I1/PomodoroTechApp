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
        var session = sessionFactory()
        timer = CustomTimer((session.getDurationInSeconds() * 1000).toLong(), object : CustomTimer.TimerListener {
            override fun onFinish() {
                session.onFinish()
                timerInit()
                viewState.showEndAlert()
            }

            override fun onTick(millisRemaining: Long) {
                viewState.updateTimerProgress(millisRemaining)
            }
        })
    }

    fun startTimer(){
        if (timer.state == TimerState.PAUSED)
            timer.resume()
        else
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
        if (timer.state == TimerState.STOPPED) {
            viewState.setTimerMax(timer.durationInMillis)
            viewState.updateTimerProgress(timer.durationInMillis)
        }
    }

    fun stopAlert(){
        viewState.showStopAlert()
    }

    fun hideAlertDialog(){
        viewState.hideAlertDialog()
    }

    fun createNotification(context: Context) {
        Log.i(TAG, timer.state.name)
        if (timer.state == TimerState.RUNNING)
            TimerNotification.create(timer.millisRemaining, context)
    }

    fun deleteNotification(context: Context) {
        TimerNotification.delete(context)
    }
}