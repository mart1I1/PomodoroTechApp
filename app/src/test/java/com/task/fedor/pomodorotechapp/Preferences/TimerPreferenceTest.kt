package com.task.fedor.pomodorotechapp.Preferences

import com.task.fedor.pomodorotechapp.SessionType
import com.task.fedor.pomodorotechapp.TimerState
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(RobolectricTestRunner::class)
class TimerPreferenceTest {

    private var timerPreference = TimerPreference(RuntimeEnvironment.application)

    @Test
    fun getState() {
        assertEquals(timerPreference.defaultState, timerPreference.state)
    }

    @Test
    fun setState() {
        timerPreference.state = TimerState.RUNNING
        assertEquals(TimerState.RUNNING, timerPreference.state)
    }

    @Test
    fun getProgressInSeconds() {
        assertEquals(timerPreference.defaultSecondsRemaining, timerPreference.secondsRemaining)
    }

    @Test
    fun setProgressInSeconds() {
        val progress = 10
        timerPreference.secondsRemaining = 10
        assertEquals(progress, timerPreference.secondsRemaining)
    }

    @Test
    fun getSessionType() {
        assertEquals(timerPreference.defaultSessionType, timerPreference.sessionType)
    }

    @Test
    fun setSessionType() {
        val sessionType = SessionType.BREAK
        timerPreference.sessionType = sessionType
        assertEquals(sessionType, timerPreference.sessionType)
    }

    @Test
    fun getBreakSessionCounter() {
        assertEquals(timerPreference.defaultBreakSessionCounter, timerPreference.breakSessionCounter)
    }

    @Test
    fun setBreakSessionCounter() {
        val counter = 3
        timerPreference.breakSessionCounter = counter
        assertEquals(counter, timerPreference.breakSessionCounter)
    }

    @Test
    fun getWorkDurationInSec() {
        assertEquals(timerPreference.defaultWorkDurationInSec, timerPreference.workDurationInSec)
    }

    @Test
    fun setWorkDurationInSec() {
        val duration = 1000
        timerPreference.workDurationInSec = duration
        assertEquals(duration, timerPreference.workDurationInSec)
    }

    @Test
    fun getBreakDurationInSec() {
        assertEquals(timerPreference.defaultBreakDurationInSec, timerPreference.breakDurationInSec)
    }

    @Test
    fun setBreakDurationInSec() {
        val duration = 1000
        timerPreference.breakDurationInSec = duration
        assertEquals(duration, timerPreference.breakDurationInSec)
    }

    @Test
    fun getLongBreakDurationInSec() {
        assertEquals(timerPreference.defaultLongBreakDurationInSec, timerPreference.longBreakDurationInSec)
    }

    @Test
    fun setLongBreakDurationInSec() {
        val duration = 1000
        timerPreference.longBreakDurationInSec = duration
        assertEquals(duration, timerPreference.longBreakDurationInSec)
    }

    @Test
    fun getLongBreakPeriod() {
        assertEquals(timerPreference.defaultLongBreakPeriod, timerPreference.longBreakPeriod)
    }

    @Test
    fun setLongBreakPeriod() {
        val period = 10
        timerPreference.longBreakPeriod = period
        assertEquals(period, timerPreference.longBreakPeriod)
    }

    @Test
    fun getAlarmMelody() {
        assertEquals(timerPreference.defaultAlarmMelody, timerPreference.alarmMelody)
    }

    @Test
    fun setAlarmMelody() {
        val melody = "hello"
        timerPreference.alarmMelody = melody
        assertEquals(melody, timerPreference.alarmMelody)
    }
}