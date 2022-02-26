package com.ankushgrover.hourglass

import com.ankushgrover.hourglass.HourglassListener
import com.ankushgrover.hourglass.Hourglass
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import kotlin.jvm.Synchronized

abstract class Hourglass : HourglassListener {
    /**
     * Convenience method to check whether the timer is running or not
     *
     * @return: true if timer is running, else false.
     */
    var isRunning = false
        private set
    /**
     * Method to check whether the timer is paused.
     *
     * @return: true if timer is paused else false.
     */
    /**
     * To pause the timer from Main thread.
     *
     * @param isPaused: true to pause the timer, false to resume.
     */
    /**
     * To maintain Timer resume and pause status.
     */
    @get:Synchronized
    @set:Synchronized
    var isPaused = false
        private set

    /**
     * Timer time.
     */
    private var time: Long = 0
    private var localTime: Long = 0
    private var interval: Long = 0
    private var handler: Handler? = null

    constructor() {
        init(0, INTERVAL.toLong())
    }

    constructor(timeInMillis: Long) {
        init(timeInMillis, INTERVAL.toLong())
    }

    constructor(timeInMillis: Long, intervalInMillis: Long) {
        init(timeInMillis, intervalInMillis)
    }

    /**
     * Method to initialize HourGlass.
     *
     * @param time:     Time in milliseconds.
     * @param interval: in milliseconds.
     */
    private fun init(time: Long, interval: Long) {
        setTime(time)
        setInterval(interval)
        initHourglass()
    }

    @SuppressLint("HandlerLeak")
    private fun initHourglass() {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG) {
                    if (!isPaused) {
                        if (localTime <= time) {
                            onTimerTick(time - localTime)
                            localTime += interval
                            sendMessageDelayed(handler!!.obtainMessage(MSG), interval)
                        } else {
                            stopTimer()
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to start the timer.
     */
    fun startTimer() {
        if (isRunning) return
        isRunning = true
        isPaused = false
        localTime = 0
        handler!!.sendMessage(handler!!.obtainMessage(MSG))
    }

    /**
     * Method to stop the timer.
     */
    fun stopTimer() {
        isRunning = false
        handler!!.removeMessages(MSG)
        onTimerFinish()
    }

    /**
     * Convenience method to pause the timer.
     */
    @Synchronized
    fun pauseTimer() {
        isPaused = true
    }

    /**
     * Convenience method to resume the timer.
     */
    @Synchronized
    fun resumeTimer() {
        isPaused = false
        handler!!.sendMessage(handler!!.obtainMessage(MSG))
    }

    /**
     * Setter for Time.
     *
     * @param timeInMillis: in milliseconds
     */
    fun setTime(timeInMillis: Long) {
        var timeInMillis = timeInMillis
        if (isRunning) return
        if (time <= 0) if (timeInMillis < 0) timeInMillis *= -1
        time = timeInMillis
    }

    /**
     * @return remaining time
     */
    val remainingTime: Long
        get() = if (isRunning) {
            time
        } else 0

    /**
     * Setter for interval.
     *
     * @param intervalInMillis: in milliseconds
     */
    fun setInterval(intervalInMillis: Long) {
        var intervalInMillis = intervalInMillis
        if (isRunning) return
        if (interval <= 0) if (intervalInMillis < 0) intervalInMillis *= -1
        interval = intervalInMillis
    }

    companion object {
        private const val INTERVAL = 1000
        private const val MSG = 1
    }
}