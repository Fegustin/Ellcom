package ru.steilsouth.ellcom.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.steilsouth.ellcom.MainActivity
import ru.steilsouth.ellcom.R
import java.io.IOException

class RadioService : Service() {

    private val channelID = "ForegroundService Kotlin"

    private val actionStop = "Stop"
    private val actionPlay = "Play"
    private val actionPause = "Pause"

    companion object {

        private val mediaPlayer = MediaPlayer()

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, RadioService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)

            val url = "http://77.232.167.247:1024/"

            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            try {
                mediaPlayer.setDataSource(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                mediaPlayer.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }

        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, RadioService::class.java)
            context.stopService(stopIntent)

            mediaPlayer.reset()
            mediaPlayer.stop()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            actionPause -> {
                mediaPlayer.pause()
            }
            actionPlay -> {
                mediaPlayer.start()
            }
            actionStop -> {
                stopService(this)
            }
        }


        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            0
        )

        val mediaSession = MediaSessionCompat(this, "tag")

        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Радио")
            .setContentText(input)
            .setSmallIcon(R.drawable.europa_plus)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_radio_pause, "Pause", pendingIntentButton(actionPause))
            .addAction(R.drawable.ic_radio_play, "Play", pendingIntentButton(actionPlay))
            .addAction(R.drawable.ic_radio_stop, "Stop", pendingIntentButton(actionStop))
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        mediaPlayer.stop()
        return super.stopService(name)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun pendingIntentButton(buttonAction: String): PendingIntent {
        val notificationIntentStop = Intent(this, RadioService::class.java).apply {
            action = buttonAction
        }
        return PendingIntent.getService(
            this,
            0,
            notificationIntentStop,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }
}