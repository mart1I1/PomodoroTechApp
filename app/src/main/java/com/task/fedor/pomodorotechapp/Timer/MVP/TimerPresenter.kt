package com.task.fedor.pomodorotechapp.Timer.MVP

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType
import com.task.fedor.pomodorotechapp.Sessions.*
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.BaseCustomTimer
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.PreferenceCustomTimer
import com.task.fedor.pomodorotechapp.TimerState

@InjectViewState
class TimerPresenter : MvpPresenter<TimerView>(), ITimerPresenter {
    val TAG = "TimerPresenter"

    lateinit var timer : BaseCustomTimer
        private set

    override fun initTimer(timerPreference: TimerPreference) {
        val session = sessionFactory(timerPreference)
        val timerListener = object : BaseCustomTimer.TimerListener {
            override fun onFinish() {
                session.onFinish()
                initTimer(timerPreference)
                viewState.runFinishAlert()
            }

            override fun onTick(secondsRemaining: Int) {
                viewState.setTimerProgress(secondsRemaining)
            }
        }
        timer = PreferenceCustomTimer(
                session.getDurationInSeconds(),
                timerListener,
                timerPreference)

        updateView()
    }

    override fun startTimer(){
        timer.start()
        updateView()
    }

    override fun stopTimer(){
        timer.stop()
        updateView()
    }

    override fun pauseTimer(){
        timer.pause()
        updateView()
    }

    override fun skipTimer() {
        timer.skip()
        updateView()
    }

    private fun sessionFactory(timerPreference: TimerPreference): Session {
        return when (timerPreference.sessionType){
            SessionType.WORK -> {
                WorkSession(timerPreference)
            }
            SessionType.BREAK -> {
                BreakSession(timerPreference)
            }
            SessionType.LONG_BREAK -> {
                LongBreakSession(timerPreference)
            }
        }
    }

    override fun updateView() {
        viewState.updateBtn(timer.state)
        viewState.setTimerMax(timer.durationInSec)
        when(timer.state) {
            TimerState.STOPPED -> viewState.setTimerProgress(timer.durationInSec)
            TimerState.PAUSED -> viewState.setTimerProgress(timer.secondsRemaining)
        }
    }

    override fun showStopAlertDialog(){
        viewState.updateBtn(TimerState.OFF)
        viewState.showStopAlertDialog()
    }

    override fun hideAlertDialogs(){
        viewState.hideAlertDialogs()
    }

    override fun releaseAlertMedia() {
        viewState.releaseAlertMedia()
    }


}