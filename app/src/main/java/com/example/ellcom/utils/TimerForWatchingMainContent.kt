package com.example.ellcom.utils

import android.os.CountDownTimer
import android.view.View

fun timerForWatchingMainContent(progressBar: View, content: View) {
    object : CountDownTimer(700, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            progressBar.visibility = View.GONE
            content.visibility = View.VISIBLE
        }
    }.start()
}