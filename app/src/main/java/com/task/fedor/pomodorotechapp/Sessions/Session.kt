package com.task.fedor.pomodorotechapp.Sessions

import com.task.fedor.pomodorotechapp.SessionType

abstract class Session : ISession {

    abstract override fun getDurationInSeconds() : Int
    override fun onFinish(){
        prepNextSession()
    }
    protected open fun prepNextSession() {
        setNextSession()
    }
    protected abstract fun setNextSession()
}