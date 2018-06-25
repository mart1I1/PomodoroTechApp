package com.task.fedor.pomodorotechapp.Timer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import com.task.fedor.pomodorotechapp.BuildConfig
import com.task.fedor.pomodorotechapp.Converter
import com.task.fedor.pomodorotechapp.R
import com.task.fedor.pomodorotechapp.R.id.action_bar_activity_content
import com.task.fedor.pomodorotechapp.R.id.barTimer
import com.task.fedor.pomodorotechapp.Timer.AlertMedia.CustomMediaPlayer
import com.task.fedor.pomodorotechapp.Timer.AlertMedia.TimerAlertMedia
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, minSdk = 19)
class TimerActivityTest {

    private lateinit var activity: TimerActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(TimerActivity::class.java).create().start().resume().get()
    }

    @Test
    fun setTimerMax() {
        val max = 10
        activity.setTimerMax(max)
        val progressBar = activity.findViewById(R.id.barTimer) as ProgressBar
        assertEquals(max, progressBar.max)
    }

    @Test
    fun setTimerProgress() {
        val progress = 10
        activity.setTimerProgress(progress)
        val textView = activity.findViewById(R.id.textTimer) as TextView
        val progressBar = activity.findViewById(R.id.barTimer) as ProgressBar

        assertEquals(Converter.timeInStringFrom(progress), textView.text)
        assertEquals(progress, progressBar.progress)
    }

    @Test
    fun showStopAlertDialog() {
        activity.showStopAlertDialog()

        assertNotNull(activity.stopAlertDialog)
        assertTrue(activity.stopAlertDialog!!.isShowing)

        assertTrue(shadowOf(activity.stopAlertDialog).isCancelable)
    }

    @Test
    fun showFinishAlertDialog() {
        activity.showFinishAlertDialog()

        assertNotNull(activity.finishAlertDialog)
        assertTrue(activity.finishAlertDialog!!.isShowing)

        assertFalse(shadowOf(activity.finishAlertDialog).isCancelable)
    }

    @Test
    fun hideAlertDialogs() {
        activity.showStopAlertDialog()
        activity.showFinishAlertDialog()

        activity.hideAlertDialogs()

        assertFalse(activity.finishAlertDialog!!.isShowing)
        assertFalse(activity.stopAlertDialog!!.isShowing)
    }
}

