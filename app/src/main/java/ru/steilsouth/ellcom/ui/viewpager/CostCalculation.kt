package ru.steilsouth.ellcom.ui.viewpager

import android.widget.TextView

fun costCalculation(progress: Int, ratePrice: Int, textView: TextView): Int {
    var price = 0
    for (i in 1..progress) {
        price += ratePrice
    }

    return if (progress == 0) {
        textView.text = "0 руб."
        0
    } else {
        textView.text = price.toString() + " руб."
        price
    }
}