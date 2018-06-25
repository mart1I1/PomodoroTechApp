package com.task.fedor.pomodorotechapp.Timer.Notification

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews
import com.task.fedor.pomodorotechapp.R
import com.task.fedor.pomodorotechapp.Converter
import com.task.fedor.pomodorotechapp.Timer.CustomTimer.BaseCustomTimer
import com.task.fedor.pomodorotechapp.Timer.TimerActivity
import com.task.fedor.pomodorotechapp.Timer.AlertMedia.TimerAlertMedia


object TimerNotification {

    private const val TAG = "Notification"
    private const val NOTIFICATION_ID = 77

    private lateinit var timer: BaseCustomTimer
    private lateinit var notificationManager: NotificationManager

    fun create(durationInSec : Int, context: Context) {
        val notificationIntent = Intent(context, TimerActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
                context,
                10,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val remoteViews = getRemoteView(context)
        val builder = NotificationCompat.Builder(context)
        builder.setContentIntent(contentIntent)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(getChannelId(NOTIFICATION_ID.toString(), context.resources.getString(R.string.timer)))
        }

        timer = BaseCustomTimer(durationInSec, object : BaseCustomTimer.TimerListener {
            override fun onFinish() {
                updateRemoteView(remoteViews, 0, builder)
                TimerAlertMedia.play(context)
            }
            override fun onTick(secondsRemaining: Int) {
                updateRemoteView(remoteViews, secondsRemaining, builder)
            }
        })
        timer.start()
    }

    fun delete() {
        if (this::timer.isInitialized && this::notificationManager.isInitialized) {
            notificationManager.cancel(NOTIFICATION_ID)
            timer.stop()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun getChannelId(channelId: String, name: String): String {
        val channels = notificationManager.notificationChannels
        try {
            return channels.first { it.id == channelId }.id
        } catch(e : NoSuchElementException){}

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, name, importance)
        notificationManager.createNotificationChannel(notificationChannel)

        return channelId
    }

    private fun updateRemoteView(remoteViews: RemoteViews, seconds : Int, builder : NotificationCompat.Builder) {
        remoteViews.setTextViewText(R.id.notification_tv, Converter.timeInStringFrom(seconds))
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun getRemoteView(context: Context): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification)
    }
}