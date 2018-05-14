package com.task.fedor.pomodorotechapp.Timer

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerPresenter
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerState
import com.task.fedor.pomodorotechapp.Timer.MVP.TimerView
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.R
import kotlinx.android.synthetic.main.activity_main.*

class TimerActivity : MvpAppCompatActivity(), TimerView {

    val TAG = "TimerActivity"

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var timerPresenter: TimerPresenter
    var stopAlertDialog : AlertDialog? = null
    var finishAlertDialog : AlertDialog? = null

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideTimerPresenter() : TimerPresenter {
        var timerPresenter = TimerPresenter()
        timerPresenter.timerPreference = TimerPreference(applicationContext)
        return timerPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener{
            timerPresenter.startTimer()
        }

        btn_stop.setOnClickListener{
            timerPresenter.stopAlert()
        }

        btn_pause.setOnClickListener{
            timerPresenter.pauseTimer()
        }
    }

    override fun setTimerMax(max: Int) {
        barTimer.max = max
    }

    override fun updateTimerProgress(seconds : Int) {
        barTimer.progress = seconds
        textTimer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    override fun showStopAlert() {
        if (!(stopAlertDialog != null && stopAlertDialog!!.isShowing)) {
            stopAlertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.timer))
                    .setMessage(getString(R.string.stop_question))
                    .setPositiveButton(getString(R.string.answer_yes), { _, _ -> run {
                        timerPresenter.stopTimer()
                        timerPresenter.hideAlertDialog()
                    } })
                    .setNegativeButton(getString(R.string.answer_no), { _, _ -> timerPresenter.hideAlertDialog()})
                    .setOnCancelListener {
                        timerPresenter.hideAlertDialog()
                    }
                    .show()
        }
    }

    override fun showFinishAlert() {
        finishAlertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.timer)
                .setMessage(getString(R.string.session_finish))
                .setPositiveButton(getString(R.string.OK), { _, _ -> run {
                    timerPresenter.updateView()
                    timerPresenter.hideAlertDialog()
                }})
                .setCancelable(false)
                .show()
    }

    override fun hideAlertDialog() {
        if (stopAlertDialog != null)
            stopAlertDialog!!.dismiss()
        if (finishAlertDialog != null)
            finishAlertDialog!!.dismiss()

    }

    override fun updateUIBtn(state: TimerState) {
        when(state){
            TimerState.STOPPED -> {
                btn_start.visibility = View.VISIBLE
                btn_pause.visibility = View.INVISIBLE
                btn_stop.visibility = View.INVISIBLE
            }
            TimerState.PAUSED -> {
                btn_start.visibility = View.VISIBLE
                btn_pause.visibility = View.INVISIBLE
                btn_stop.visibility = View.VISIBLE
            }
            TimerState.RUNNING -> {
                btn_start.visibility = View.INVISIBLE
                btn_pause.visibility = View.VISIBLE
                btn_stop.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timerPresenter.createNotification(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        timerPresenter.deleteNotification(applicationContext)
        timerPresenter.deleteInstance()
    }

    override fun onStop() {
        super.onStop()
        timerPresenter.saveInstanceOnStop()
    }

}
