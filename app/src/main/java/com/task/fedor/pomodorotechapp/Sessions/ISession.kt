package com.task.fedor.pomodorotechapp.Sessions

interface ISession {
    fun getDurationInSeconds() : Int
    fun onFinish()
}