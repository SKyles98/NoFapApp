package com.saleef.temperedvolition

import android.os.CountDownTimer
// A timer class tweaks the countdown timer slightly to count up from 0 instead of down from (X)
abstract class CountUpTimer(private val secondsInFuture: Int, countUpIntervalSeconds: Int)
    // Converts our passed in seconds to millis for tick
    :CountDownTimer(secondsInFuture.toLong() * 1000, countUpIntervalSeconds.toLong() * 1000) {

    abstract fun onCount(count: Int)
    // ms is our millis in future that
    override fun onTick(msUntilFinished: Long) { // subtract from millisecond duration and then
        onCount(((secondsInFuture.toLong() * 1000 - msUntilFinished) / 1000).toInt())
    }

}