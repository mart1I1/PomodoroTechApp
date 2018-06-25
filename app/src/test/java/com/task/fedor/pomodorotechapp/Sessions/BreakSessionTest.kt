package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.Preferences.TimerPreference
import com.task.fedor.pomodorotechapp.SessionType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BreakSessionTest {

    private lateinit var timerPreference : TimerPreference
    private lateinit var session : BreakSession

    @Before
    fun setUp(){
        timerPreference = mock(TimerPreference::class.java)
        session = BreakSession(timerPreference)
    }

    @Test
    fun prepNextSession_Next_Session() {
        session.onFinish()
        verify(timerPreference).sessionType = SessionType.WORK
    }

    @Test
    fun prepNextSession_Counter_Incr() {
        val counter = 0
        `when`(timerPreference.breakSessionCounter).thenReturn(counter)

        session.onFinish()
        verify(timerPreference).breakSessionCounter = counter + 1
    }
}