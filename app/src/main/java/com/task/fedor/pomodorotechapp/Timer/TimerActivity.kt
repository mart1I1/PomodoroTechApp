package com.task.fedor.pomodorotechapp.Timer

import android.app.AlertDialog
import android.content.Intent
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
    lateinit var endAlertDialog : AlertDialog

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

        test.setOnClickListener {
            startActivity(Intent(applicationContext, TimerActivity::class.java))
        }
    }

    override fun setTimerMax(max: Long) {
        barTimer.max = (max / 1000).toInt()
    }

    override fun updateTimerProgress(millis : Long) {
        var seconds = (millis / 1000).toInt()
        barTimer.progress = seconds
        textTimer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    override fun showStopAlert() {
        if (!(stopAlertDialog != null && stopAlertDialog!!.isShowing)) {
            stopAlertDialog = AlertDialog.Builder(this)
                    .setTitle("Таймер")
                    .setMessage("Остановить таймер?")
                    .setPositiveButton("Да", { _, _ -> run {
                        timerPresenter.stopTimer()
                        timerPresenter.hideAlertDialog()
                    } })
                    .setNegativeButton("Нет", { _, _ -> timerPresenter.hideAlertDialog()})
                    .setOnCancelListener {
                        timerPresenter.hideAlertDialog()
                    }
                    .show()
        }
    }

    override fun showEndAlert() {
        endAlertDialog = AlertDialog.Builder(this)
                .setTitle("Таймер")
                .setMessage("Сессия окончена")
                .setPositiveButton("Ок", { _, _ -> run {
                    timerPresenter.updateView()
                    timerPresenter.hideAlertDialog()
                }})
                .setCancelable(false)
                .show()
    }

    override fun hideAlertDialog() {
        if (stopAlertDialog != null)
            stopAlertDialog!!.dismiss()
        if (this::endAlertDialog.isInitialized)
            endAlertDialog.dismiss()

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
    }

}
