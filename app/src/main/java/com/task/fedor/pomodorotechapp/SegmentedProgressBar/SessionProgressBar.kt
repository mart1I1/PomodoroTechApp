package com.task.fedor.pomodorotechapp.SegmentedProgressBar

import android.content.Context
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.task.fedor.pomodorotechapp.R
import com.task.fedor.pomodorotechapp.SessionType

object SessionProgressBar {
    fun create(sessionType: SessionType, duration : Int, id : Int, context: Context) : ProgressBar {
        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progressBar = when(sessionType) {
            SessionType.WORK -> inflater.inflate(R.layout.progress_bar_work, null) as ProgressBar
            else -> inflater.inflate(R.layout.progress_bar_break, null) as ProgressBar
        }
        progressBar.id = id

        val weight = when(sessionType) {
            SessionType.WORK -> 3f
            SessionType.BREAK -> 1f
            SessionType.LONG_BREAK -> 2f
        }
        val params = LinearLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, weight)
        progressBar.layoutParams = params
        progressBar.max = duration
        return progressBar
    }
}