package com.example.ellcom.service

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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.ellcom.MainActivity
import com.example.ellcom.R
import java.io.IOException

class RadioService : Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"

    companion object {

        private val mediaPlayer = MediaPlayer()

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, RadioService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)

            val url = "http://ep256.streamr.ru"

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
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Радио")
            .setContentText(input)
            .setSmallIcon(R.drawable.europa_plus)
            .setContentIntent(pendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat
                    .MediaStyle()
                    .setShowActionsInCompactView()
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
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}