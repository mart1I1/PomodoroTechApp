package com.task.fedor.pomodorotechapp.SegmentedProgressBar

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType
import com.task.fedor.pomodorotechapp.Timer.TimerActivity

class SegmentedProgressBar(private val context: Context) : ISegmentedProgressBar {
    private var preference = TimerPreference(context)
    private var barList = arrayListOf<ProgressBar>()
    private var id = -1

    override var progress : Int
        get() = barList[id].progress
        set(value) {
            barList[id].progress = barList[id].max - value
        }

    override fun createBars() {
        for (id in 0 until preference.longBreakPeriod * 2 - 1) {
            val sessionProgressBar =
                    if (id % 2 == 0) {
                        SessionProgressBar.create(SessionType.WORK, preference.workDurationInSec, id, context)
                    }
                    else {
                        SessionProgressBar.create(SessionType.BREAK, preference.breakDurationInSec, id, context)
                    }
            barList.add(sessionProgressBar)
        }
        barList.add(
                SessionProgressBar.create(
                        SessionType.LONG_BREAK,
                        preference.longBreakDurationInSec,
                        preference.longBreakPeriod * 2 - 1,
                        context))
    }

    override fun addBarsTo(linearLayout: LinearLayout) {
        barList.forEach { linearLayout.addView(it) }
    }

    override fun initSession() {
        val counter = preference.breakSessionCounter
        val type = preference.sessionType

        id = when (type) {
            SessionType.BREAK -> counter * 2 + 1
            SessionType.LONG_BREAK -> counter * 2 + 1
            SessionType.WORK -> counter * 2
        }

        for (i in 0 until id) barList[i].progress = barList[i].max
        for (i in id+1 until barList.size) barList[i].progress = 0
    }
}