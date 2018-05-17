package com.task.fedor.pomodorotechapp.Timer.Notification

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class NotificationKillerService : Service() {

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(TimerNotification.NOTIFICATION_ID)
        super.onTaskRemoved(rootIntent)
    }
}
