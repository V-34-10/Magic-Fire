package com.magicmex.canfiree.view.settings.vibro

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import com.magicmex.canfiree.utils.preference.PreferenceManager

@SuppressLint("StaticFieldLeak")
object VibroController {
    private val vibratorWrapper: VibratorWrapper by lazy { VibratorWrapper(context) }
    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }

    fun vibrate(duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibratorWrapper.vibrate(duration)
        } else {
            vibratorWrapper.vibrateCompat(duration)
        }
    }

    fun cancel() = vibratorWrapper.cancel()
}

object VibroStart {
    fun vibrateIfEnabled(duration: Long = 500) {
        if (PreferenceManager.vibroStatus) {
            VibroController.vibrate(duration)
        }
    }
}