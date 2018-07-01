package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.TimerState

class PreferenceCustomTimer(durationInSec: Int,
                            timerListener: TimerListener,
                            var timerPreference: TimerPreference)
    : BaseCustomTimer(durationInSec, timerListener, timerPreference.secondsRemaining) {

    override fun start() {
        super.start()
        saveState(getState())
    }

    override fun pause() {
        super.pause()
        saveState(getState())
    }

    override fun stop() {
        super.stop()
        timerPreference.secondsRemaining = getSecondsRemaining()
        saveState(getState())
    }

    override fun onTick(secondsRemaining: Int) {
        super.onTick(secondsRemaining)
        timerPreference.secondsRemaining = secondsRemaining
    }

    override fun onFinish() {
        saveState(TimerState.STOPPED)
        super.onFinish()
    }

    private fun saveState(state : TimerState) {
        timerPreference.state = state
    }
}