package com.magicmex.canfire.view.settings.vibro

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi

object VibroController {
    private fun getVibrator(context: Context): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun vibroEmulateDevice(context: Context, duration: Long) {
        val vibrator = getVibrator(context)

        if (vibrator.hasVibrator()) {
            val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            } else {
                @Suppress("DEPRECATION")
                null
            }

            if (vibrationEffect != null) {
                vibrator.vibrate(vibrationEffect)
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(duration)
            }
        }
    }

    fun vibroEmulateOff(context: Context) {
        getVibrator(context).cancel()
    }
}