package com.magicmex.canfiree.view.settings.music

import android.content.Context
import android.media.AudioManager

class MusicVolumeManager(context: Context) {
    private val managerMusic by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var defaultMusicVolume: Int = 25
    fun setMusicVolume() =
        managerMusic.setStreamVolume(AudioManager.STREAM_MUSIC, defaultMusicVolume, 0)

    fun offMusicVolume() {
        defaultMusicVolume = managerMusic.getStreamVolume(AudioManager.STREAM_MUSIC)
        managerMusic.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }
}