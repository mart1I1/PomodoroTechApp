package com.task.fedor.pomodorotechapp.Timer.CustomTimer

import com.task.fedor.pomodorotechapp.TimerState
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class BaseCustomTimerTest {

    private lateinit var listener : BaseCustomTimer.TimerListener
    private val durationInSec = 60


    @Before
    fun setUp() {
        listener = object : BaseCustomTimer.TimerListener {
            override fun onTick(secondsRemaining: Int) {
            }

            override fun onFinish() {
            }
        }
    }

    @Test
    fun constructor() {
        val timer = BaseCustomTimer(durationInSec, listener)

        assertEquals(TimerState.STOPPED, timer.state)
        assertEquals(durationInSec, timer.durationInSec)
    }

    @Test
    fun constructor_startFrom() {
        val startFrom = durationInSec - 1
        val timer = BaseCustomTimer(durationInSec, listener, startFrom)

        assertEquals(TimerState.PAUSED, timer.state)
        assertEquals(startFrom, timer.secondsRemaining)
    }

    @Test(expected = IllegalArgumentException::class)
    fun constructor_startFrom_ex() {
        val startFrom = durationInSec + 1
        var timer = BaseCustomTimer(durationInSec, listener, startFrom)
    }

    @Test
    fun start() {
        val timer = BaseCustomTimer(durationInSec, listener)
        timer.start()
        assertEquals(TimerState.RUNNING, timer.state)
    }

    @Test
    fun pause() {
        val timer = BaseCustomTimer(durationInSec, listener)
        timer.start()
        timer.pause()
        assertEquals(TimerState.PAUSED, timer.state)
    }

    @Test
    fun stop() {
        val timer = BaseCustomTimer(durationInSec, listener)
        timer.start()
        timer.stop()
        assertEquals(TimerState.STOPPED, timer.state)
        assertEquals(0, timer.secondsRemaining)
    }
}

