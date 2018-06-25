package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(MockitoJUnitRunner::class)
class LongBreakSessionTest {

    private lateinit var timerPreference : TimerPreference
    private lateinit var session : LongBreakSession

    @Before
    fun setUp(){
        timerPreference = Mockito.mock(TimerPreference::class.java)
        session = LongBreakSession(timerPreference)
    }

    @Test
    fun prepNextSession_Next_Session() {
        session.onFinish()
        Mockito.verify(timerPreference).sessionType = SessionType.WORK
    }

    @Test
    fun prepNextSession_COUNTER_ZERO() {
        session.onFinish()
        Mockito.verify(timerPreference).breakSessionCounter = 0
    }
}