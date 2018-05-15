package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState

class PreferenceCustomTimer(durationInSec: Int,
                            timerListener: TimerListener,
                            private val timerPreference : TimerPreference,
                            startFrom : Int = 0) : BaseCustomTimer(durationInSec, timerListener, startFrom) {

    constructor(baseCustomTimer: BaseCustomTimer,
                timerPreference: TimerPreference,
                startFrom: Int = 0)
            : this(baseCustomTimer.durationInSec, baseCustomTimer.timerListener, timerPreference, startFrom)

    constructor(preferenceCustomTimer: PreferenceCustomTimer,
                startFrom: Int = 0)
            : this(preferenceCustomTimer.durationInSec,
            preferenceCustomTimer.timerListener,
            preferenceCustomTimer.timerPreference,
            startFrom)

    override fun start() {
        super.start()
        saveState(this.state)
    }

    override fun pause() {
        super.pause()
        saveState(this.state)
    }

    override fun stop() {
        super.stop()
        saveState(this.state)
    }

    override fun onTick(secondsRemaining: Int) {
        super.onTick(secondsRemaining)
        timerPreference.progressInSeconds = secondsRemaining
    }

    private fun saveState(state : TimerState) {
        timerPreference.state = state
    }
}