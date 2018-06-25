package com.task.fedor.pomodorotechapp.Sessions

import android.content.Context
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WorkSessionTest {
    private lateinit var mockTimerPreference : TimerPreference
    private lateinit var session : WorkSession
    private lateinit var timerPreference: TimerPreference

    @Before
    fun setUp(){
        timerPreference = TimerPreference(mock(Context::class.java))
        mockTimerPreference = Mockito.mock(TimerPreference::class.java)
        session = WorkSession(mockTimerPreference)
    }

    @Test
    fun prepNextSession_Next_Session_Case1() {
        val counter = 0
        `when`(mockTimerPreference.breakSessionCounter).thenReturn(counter)

        session.onFinish()
        verify(mockTimerPreference).sessionType = SessionType.BREAK
    }

    @Test
    fun prepNextSession_Next_Session_Case2() {
        val counter = mockTimerPreference.defaultLongBreakPeriod - 1
        `when`(mockTimerPreference.breakSessionCounter).thenReturn(counter)

        session.onFinish()
        verify(mockTimerPreference).sessionType = SessionType.LONG_BREAK
    }
}