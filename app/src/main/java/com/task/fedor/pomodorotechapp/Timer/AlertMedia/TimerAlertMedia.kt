package com.task.fedor.pomodorotechapp.Timer.AlertMedia

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.task.fedor.pomodorotechapp.Preferences.TimerPreference

object TimerAlertMedia {
    private var mediaPlayer : CustomMediaPlayer? = null
    private var vibrator : Vibrator? = null
    private val vibratorPattern = longArrayOf(100, 100, 1000)

    fun play(context: Context) {
        if (isPlaying()) throw IllegalStateException("is playing: true")

        mediaPlayer = CustomMediaPlayer(context)
        mediaPlayer!!.start()

        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator!!.hasVibrator())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator!!.vibrate(VibrationEffect.createWaveform(vibratorPattern, 0))
            }
            else {
                vibrator!!.vibrate(vibratorPattern, 0)
            }
    }

    fun release() {
        try {
            mediaPlayer!!.release()
            vibrator!!.cancel()

            mediaPlayer = null
            vibrator = null
        } catch (e : NullPointerException) {
            throw IllegalStateException("media player not init")
        }
    }

    fun isPlaying(): Boolean {
        if (mediaPlayer == null)
            return false
        return mediaPlayer!!.isPlaying()
    }
}

