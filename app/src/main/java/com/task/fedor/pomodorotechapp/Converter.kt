package com.task.fedor.pomodorotechapp

object Converter {
    fun timeInStringFrom(timeInSec : Int) : String {
        return String.format("%02d:%02d", timeInSec / 60, timeInSec % 60)
    }
}