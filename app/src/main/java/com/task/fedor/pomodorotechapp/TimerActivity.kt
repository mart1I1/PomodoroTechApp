package com.task.fedor.pomodorotechapp

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.MVP.Timer.TimerPresenter
import com.task.fedor.pomodorotechapp.MVP.Timer.TimerState
import com.task.fedor.pomodorotechapp.MVP.Timer.TimerView
import kotlinx.android.synthetic.main.activity_main.*

class TimerActivity : MvpAppCompatActivity(), TimerView {

    val TAG = "TimerActivity"

    @InjectPresenter
    lateinit var timerPresenter: TimerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener{
            timerPresenter.startTimer()
        }

        btn_stop.setOnClickListener{
            timerPresenter.stopTimer()
        }

        btn_pause.setOnClickListener{
            timerPresenter.pauseTimer()
        }

    }

    override fun onResume() {
        super.onResume()
        timerPresenter.initPresenterAndView(TimerPreference(applicationContext))
    }

    override fun setTimerMax(max: Int) {
        barTimer.max = max
    }

    override fun updateTimerProgress(seconds: Int) {
        barTimer.progress = seconds
        textTimer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    override fun onTimerFinish() {
        timerPresenter.initPresenterAndView(TimerPreference(applicationContext))
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




}
