package com.magicmex.canfire.view.games.findgame.manager

import android.os.CountDownTimer
import androidx.viewbinding.ViewBinding
import com.magicmex.canfire.databinding.FragmentFindPairGameBinding

class TimerManager(private val binding: ViewBinding) {
    private val delayTimer = 1000L
    private var timer: CountDownTimer? = null
    private var startTime: Long = 0
    var elapsedTime: Long = 0

    fun startTimer() {
        startTime = System.currentTimeMillis()
        timer = object : CountDownTimer(Long.MAX_VALUE, delayTimer) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                updateTimeText()
            }

            override fun onFinish() {}
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
        resetTimer()
        updateTimeText()
    }

    private fun resetTimer() {
        startTime = 0
        elapsedTime = 0
    }

    private fun updateTimeText() {
        if (binding is FragmentFindPairGameBinding) binding.textTime.text = elapsedTime.formatTime()
    }

    private fun Long.formatTime(): String {
        val minutes = (this / delayTimer) / 60
        val seconds = (this / delayTimer) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}