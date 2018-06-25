package com.task.fedor.pomodorotechapp.Timer.AlertMedia

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

class CustomMediaPlayer(context: Context) {
    private var mediaPlayer: MediaPlayer

    init {
        mediaPlayer = mediaPlayerFactory(context)
    }

    fun start() {
        mediaPlayer.start()
    }

    fun release() {
        mediaPlayer.release()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    private fun mediaPlayerFactory(context: Context) : MediaPlayer {
        val mediaPlayer = MediaPlayer()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()
            )
        }
        else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
        mediaPlayer.setDataSource(context,
                Uri.parse(
                        "android.resource://"
                                + context.packageName + "/"
                                + "raw/"
                                + TimerPreference(context).alarmMelody))

        mediaPlayer.setVolume(0.0f, 0.5f)
        mediaPlayer.isLooping = true
        mediaPlayer.prepare()
        return mediaPlayer
    }
}