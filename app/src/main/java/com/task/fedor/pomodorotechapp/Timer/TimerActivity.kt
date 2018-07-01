package com.task.fedor.pomodorotechapp.Timer

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.R
import com.task.fedor.pomodorotechapp.Timer.AlertMedia.TimerAlertMedia
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerPresenter
import com.task.fedor.pomodorotechapp.TimerState
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerView
import com.task.fedor.pomodorotechapp.Timer.Notification.NotificationService
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.task.fedor.pomodorotechapp.SegmentedProgressBar.ISegmentedProgressBar
import com.task.fedor.pomodorotechapp.SegmentedProgressBar.SegmentedProgressBar
import com.task.fedor.pomodorotechapp.SessionType


class TimerActivity : MvpAppCompatActivity(), TimerView {

    val TAG = "TimerActivity"

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var timerPresenter: TimerPresenter
    var stopAlertDialog: AlertDialog? = null
    var finishAlertDialog: AlertDialog? = null
    lateinit var segmentedProgressBar : ISegmentedProgressBar

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideTimerPresenter(): TimerPresenter {
        val timerPresenter = TimerPresenter()
        timerPresenter.initTimer(TimerPreference(applicationContext))
        return timerPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSegmentedProgressBar()

        btn_start.setOnClickListener {
            timerPresenter.startTimer()
        }

        btn_stop.setOnClickListener {
            timerPresenter.showStopAlertDialog()
        }

        btn_pause.setOnClickListener {
            timerPresenter.pauseTimer()
        }

        btn_skip.setOnClickListener {
            timerPresenter.skipTimer()
        }
    }

    private fun initSegmentedProgressBar() {
        segmentedProgressBar = SegmentedProgressBar(applicationContext)
        segmentedProgressBar.createBars()
        segmentedProgressBar.addBarsTo(linear_layout_progress)
    }

    override fun setTimerMax(max: Int) {
        barTimer.max = max
    }

    override fun setTimerProgress(seconds: Int) {
        barTimer.progress = seconds
        textTimer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    override fun showStopAlertDialog() {
        stopAlertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.timer))
                .setMessage(getString(R.string.stop_question))
                .setPositiveButton(getString(R.string.answer_yes)) { _, _ ->
                    run {
                        timerPresenter.stopTimer()
                        timerPresenter.hideAlertDialogs()
                    }
                }
                .setNegativeButton(getString(R.string.answer_no)) { _, _ ->
                    run {
                        timerPresenter.hideAlertDialogs()
                        timerPresenter.updateView()
                    }
                }
                .setOnCancelListener {
                    timerPresenter.hideAlertDialogs()
                    timerPresenter.updateView()
                }
                .show()
    }

    override fun runFinishAlert() {
        playFinishAlertMedia()
        showFinishAlertDialog()
    }

    private fun playFinishAlertMedia(){
        if (!TimerAlertMedia.isPlaying()) {
            TimerAlertMedia.play(applicationContext)
        }
    }

    override fun showFinishAlertDialog(){
        finishAlertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.timer)
                .setMessage(getString(R.string.session_finish))
                .setPositiveButton(getString(R.string.OK)) { _, _ ->
                    run {
                        timerPresenter.updateView()
                        timerPresenter.hideAlertDialogs()
                        timerPresenter.releaseAlertMedia()
                    }
                }
                .setCancelable(false)
                .show()
    }


    override fun releaseAlertMedia() {
        TimerAlertMedia.release()
    }

    override fun hideAlertDialogs() {
        if (stopAlertDialog != null) {
            stopAlertDialog!!.dismiss()
        }
        if (finishAlertDialog != null) {
            finishAlertDialog!!.dismiss()
        }
    }

    override fun updateBtn(state: TimerState) {
        when (state) {
            TimerState.STOPPED -> {
                btn_start.visibility = View.VISIBLE
                btn_pause.visibility = View.INVISIBLE

                btn_stop.isEnabled = false
                btn_skip.isEnabled = false
            }
            TimerState.PAUSED -> {
                btn_start.visibility = View.VISIBLE
                btn_pause.visibility = View.INVISIBLE

                btn_stop.isEnabled = true
                btn_skip.isEnabled = true
            }
            TimerState.RUNNING -> {
                btn_start.visibility = View.INVISIBLE
                btn_pause.visibility = View.VISIBLE

                btn_stop.isEnabled = true
                btn_skip.isEnabled = true
            }
            TimerState.OFF -> {
                btn_start.visibility = View.INVISIBLE
                btn_pause.visibility = View.INVISIBLE

                btn_stop.isEnabled = false
                btn_skip.isEnabled = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (timerPresenter.timer.getState() == TimerState.RUNNING) {
            val intent = Intent(applicationContext, NotificationService::class.java)
            intent.putExtra("secondsRemaining", timerPresenter.timer.getSecondsRemaining())
            startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        stopService(Intent(applicationContext, NotificationService::class.java))
    }

    override fun setSegmentedProgress(progress: Int) {
        segmentedProgressBar.progress = progress
    }

    override fun initSegmentedProgressBarSession() {
        segmentedProgressBar.initSession()
    }
}
