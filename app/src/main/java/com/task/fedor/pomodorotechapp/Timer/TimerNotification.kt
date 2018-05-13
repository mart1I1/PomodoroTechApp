package com.task.fedor.pomodorotechapp.Timer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.os.Build
import android.annotation.TargetApi
import android.graphics.Color
import com.task.fedor.pomodorotechapp.R


object TimerNotification {

    private const val TAG = "Notification"

    private const val NOTIFICATION_ID = 77

    private lateinit var timer: CustomTimer

    fun create(durationInMillis : Long, context: Context) {
        val notificationIntent = Intent(context, TimerActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
                context,
                10,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(context)
        builder.setContentIntent(contentIntent)
                .setContentTitle("To do")
                .setContentText("hello")
                .setSmallIcon(R.drawable.notification_template_icon_low_bg)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(getChannelId("timer" + NOTIFICATION_ID.toString(), "Timer", context))
        }

        timer = CustomTimer(durationInMillis, object : CustomTimer.TimerListener {
            override fun onFinish() {
                builder.setContentText("00:00")
                notificationManager.notify(NOTIFICATION_ID, builder.build())
            }

            override fun onTick(millisRemaining: Long) {
                var seconds = (millisRemaining / 1000).toInt()
                builder.setContentText(String.format("%02d:%02d", seconds / 60, seconds % 60))
                notificationManager.notify(NOTIFICATION_ID, builder.build())
            }
        })
        timer.start()
    }

    fun delete(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
        if (this::timer.isInitialized)
            timer.stop()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun getChannelId(channelId: String, name: String, context: Context): String? {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channels = notificationManager.notificationChannels

        try {
            return channels.first { it.id == channelId }.id
        } catch(e : NoSuchElementException) {
        }

        val importance = NotificationManager.IMPORTANCE_LOW
        val notificationChannel = NotificationChannel(channelId, name, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED

        notificationManager.createNotificationChannel(notificationChannel)

        return channelId
    }
}