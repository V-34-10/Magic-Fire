package com.magicmex.canfiree.view.splash

import android.animation.ValueAnimator
import android.content.Context
import android.view.View

object LoadingBarManager {
    fun View.startLoadingAnimation(duration: Long = 3000L, maxWidth: Int) {
        val layoutParams = this.layoutParams
        ValueAnimator.ofInt(10, maxWidth).apply {
            this.duration = duration
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                this@startLoadingAnimation.layoutParams = layoutParams
            }
            start()
        }
    }

    fun Context.getProgressBarWidth(): Int {
        val displayMetrics = resources.displayMetrics
        return (350 * displayMetrics.density).toInt() - 10
    }
}