package com.task.fedor.pomodorotechapp.Timer.Notification

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build.VERSION_CODES.N
import android.os.IBinder

class NotificationService : Service() {

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        TimerNotification.create(intent.extras.getInt("secondsRemaining"), this)
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        TimerNotification.delete()
    }

    override fun onDestroy() {
        super.onDestroy()
        TimerNotification.delete()
    }
}
