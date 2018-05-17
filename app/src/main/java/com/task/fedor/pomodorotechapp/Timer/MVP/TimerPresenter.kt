package com.task.fedor.pomodorotechapp.Timer.MVP

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.task.fedor.pomodorotechapp.Timer.Notification.NotificationKillerService
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.Sessions.*
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.BaseCustomTimer
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.PreferenceCustomTimer
import com.task.fedor.pomodorotechapp.Timer.Notification.TimerNotification

@InjectViewState
class TimerPresenter : MvpPresenter<TimerView>() {
    val TAG = "TimerPresenter"

    lateinit var timerPreference: TimerPreference
    lateinit var timer : BaseCustomTimer
        private set

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        timerInit()
        updateView()
    }

    private fun timerInit() {
        val session = sessionFactory()
        val timerListener = object : BaseCustomTimer.TimerListener {
            override fun onFinish() {
                session.onFinish()
                timerInit()
                viewState.showFinishAlert()
            }

            override fun onTick(secondsRemaining: Int) {
                viewState.updateTimerProgress(secondsRemaining)
                timerPreference.progressInSeconds = secondsRemaining
            }
        }

        val currTimer = PreferenceCustomTimer(session.getDurationInSeconds(), timerListener, timerPreference)
        timer = if (timerPreference.state != TimerState.STOPPED){
            PreferenceCustomTimer(currTimer, timerPreference.progressInSeconds)
        } else {
            currTimer
        }
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
        if (timer.state == TimerState.RUNNING) {
            TimerNotification.create(timer.secondsRemaining, context)
            context.startService(Intent(context, NotificationKillerService::class.java))
        }
    }

    fun deleteNotification() {
        TimerNotification.delete()
    }
}