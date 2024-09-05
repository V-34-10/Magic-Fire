package com.magicmex.canfiree.view.settings.music

import android.content.Context
import androidx.annotation.RawRes
import com.magicmex.canfiree.utils.preference.PreferenceManager

class MusicController(private val context: Context) {
    private val soundPlayers = mutableMapOf<Int, SoundPlayer>()

    fun playSound(@RawRes soundResId: Int, loop: Boolean = false) {
        val soundPlayer = soundPlayers.getOrPut(soundResId) {
            SoundPlayer(context)
        }
        soundPlayer.play(soundResId, loop)
    }

    fun pause() = soundPlayers.values.forEach { it.pause() }

    fun resume() = soundPlayers.values.forEach { it.resume() }

    fun release() {
        soundPlayers.values.forEach { it.release() }
        soundPlayers.clear()
    }
}

object MusicStart {
    fun musicStartMode(sourceMusic: Int, managerMusic: MusicController) {
        if (PreferenceManager.musicStatus) managerMusic.apply { playSound(sourceMusic, true) }
    }
}