package com.task.fedor.pomodorotechapp.Timer

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import com.task.fedor.pomodorotechapp.Converter
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.R
import com.task.fedor.pomodorotechapp.TimerState
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.action.ViewActions.pressBack
import com.task.fedor.pomodorotechapp.SessionType


@RunWith(AndroidJUnit4::class)
class TimerActivityTestUI {

    @get:Rule
    var activityRule = ActivityTestRule(TimerActivity::class.java)

    lateinit var timerPreference : TimerPreference

    @Before
    fun setUp() {
        timerPreference = TimerPreference(InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun appStart() {
        assertStopState(timerPreference.workDurationInSec)
    }

    @Test
    fun stopBtnState() {
        assertButtonsStop()
    }

    @Test
    fun pauseBtnState() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_pause)).perform(click())

        assertButtonsPause()

        stopTimer()
    }

    @Test
    fun runningBtnState() {
        onView(withId(R.id.btn_start)).perform(click())

        assertButtonsRunning()

        stopTimer()
    }

    @Test
    fun clickStartFromStop() {
        onView(withId(R.id.btn_start)).perform(click())

        assertRunningState()

        stopTimer()
    }

    @Test
    fun clickPause() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_pause)).perform(click())

        assertPauseState()

        stopTimer()
    }

    @Test
    fun clickStopFromPause() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_pause)).perform(click())
        stopTimer()

        assertStopState(timerPreference.workDurationInSec)
    }

    @Test
    fun clickStopFromRunning() {
        onView(withId(R.id.btn_start)).perform(click())
        stopTimer()

        assertStopState(timerPreference.workDurationInSec)
    }

    @Test
    fun clickStartFromPause() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_pause)).perform(click())
        onView(withId(R.id.btn_start)).perform(click())

        assertRunningState()
    }

    @Test
    fun stopAlertDialogClickYes() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_stop)).perform(click())

        onView(withText(R.string.stop_question)).check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())

        assertStopState(timerPreference.workDurationInSec)
    }

    @Test
    fun stopAlertDialogClickNo() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_stop)).perform(click())

        onView(withText(R.string.stop_question)).check(matches(isDisplayed()))
        onView(withId(android.R.id.button2)).perform(click())

        assertRunningState()

        stopTimer()
    }

    @Test
    fun stopAlertDialogCancel() {
        onView(withId(R.id.btn_start)).perform(click())
        onView(withId(R.id.btn_stop)).perform(click())

        onView(withText(R.string.stop_question)).check(matches(isDisplayed()))
        onView(withText(R.string.stop_question)).perform(pressBack())

        assertRunningState()

        stopTimer()
    }

    @Test
    fun finish() {
        timerPreference.secondsRemaining = 2

        activityRule.activity.runOnUiThread { activityRule.activity.timerPresenter.initTimer(timerPreference) }
        onView(withId(R.id.btn_start)).perform(click())
        Thread.sleep(3*1000)

        onView(withText(R.string.session_finish)).check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())

        Thread.sleep(1000)

        assertStopState(timerPreference.breakDurationInSec)

        timerPreference.sessionType = SessionType.WORK
        activityRule.activity.runOnUiThread { activityRule.activity.timerPresenter.initTimer(timerPreference) }
    }

    private fun assertButtonsPause() {
        onView(withId(R.id.btn_stop)).check(matches((isEnabled())))
        onView(withId(R.id.btn_pause)).check(matches(not(isEnabled())))
        onView(withId(R.id.btn_start)).check(matches((isEnabled())))
    }

    private fun assertButtonsRunning() {
        onView(withId(R.id.btn_stop)).check(matches((isEnabled())))
        onView(withId(R.id.btn_pause)).check(matches((isEnabled())))
        onView(withId(R.id.btn_start)).check(matches(not(isEnabled())))
    }

    private fun assertButtonsStop() {
        onView(withId(R.id.btn_stop)).check(matches(not(isEnabled())))
        onView(withId(R.id.btn_pause)).check(matches(not(isEnabled())))
        onView(withId(R.id.btn_start)).check(matches((isEnabled())))
    }

    private fun assertStopState(duration: Int) {
        assertEquals(TimerState.STOPPED, timerPreference.state)
        onView(withId(R.id.textTimer)).check(matches(withText(Converter.timeInStringFrom(duration))))
        onView(withId(R.id.barTimer)).check(matches(isDisplayed()))
        assertEquals(duration, activityRule.activity.barTimer.progress)
        assertEquals(duration, activityRule.activity.barTimer.max)
    }

    private fun assertPauseState() {
        assertEquals(TimerState.PAUSED, timerPreference.state)
        onView(withId(R.id.textTimer)).check(matches(withText(Converter.timeInStringFrom(timerPreference.secondsRemaining))))
        onView(withId(R.id.barTimer)).check(matches(isDisplayed()))
        assertEquals(timerPreference.secondsRemaining, activityRule.activity.barTimer.progress)
        assertEquals(timerPreference.workDurationInSec, activityRule.activity.barTimer.max)
    }

    private fun assertRunningState() {
        assertEquals(TimerState.RUNNING, timerPreference.state)
        Thread.sleep(3*1000)

        onView(withId(R.id.textTimer))
                .check(matches(withText(Converter.timeInStringFrom(timerPreference.secondsRemaining))))
        assertEquals(timerPreference.secondsRemaining, activityRule.activity.barTimer.progress)
    }

    private fun stopTimer() {
        onView(withId(R.id.btn_stop)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
    }
}