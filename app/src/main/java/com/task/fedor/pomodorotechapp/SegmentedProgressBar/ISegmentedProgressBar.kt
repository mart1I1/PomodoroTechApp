package com.task.fedor.pomodorotechapp.SegmentedProgressBar

import android.content.Context
import android.widget.LinearLayout
import com.task.fedor.pomodorotechapp.SessionType

interface ISegmentedProgressBar {
    var progress : Int
    fun createBars()
    fun addBarsTo(linearLayout: LinearLayout)
    fun initSession()
}