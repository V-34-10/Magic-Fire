package com.magicmex.canfire.view.settings

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import androidx.annotation.RawRes

class MusicController(private val context: Context) {
    private val mediaPlayers = SparseArray<MediaPlayer>()
    private val handler = Handler(Looper.getMainLooper())

    fun playSound(@RawRes soundResId: Int, loop: Boolean = false) {
        val existingPlayer = mediaPlayers[soundResId]
        if (existingPlayer != null && existingPlayer.isPlaying) return

        val mediaPlayer = MediaPlayer.create(context, soundResId).apply {
            isLooping = loop
            setOnCompletionListener {
                if (!loop) handler.post { releaseMediaPlayer(soundResId) }
            }
            start()
        }

        mediaPlayers.put(soundResId, mediaPlayer)
    }

    fun stopSound(@RawRes soundResId: Int) {
        releaseMediaPlayer(soundResId)
    }

    fun pause() {
        for (i in 0 until mediaPlayers.size()) {
            mediaPlayers.valueAt(i).takeIf { it.isPlaying }?.pause()
        }
    }

    fun resume() {
        for (i in 0 until mediaPlayers.size()) {
            mediaPlayers.valueAt(i).takeIf { !it.isPlaying }?.start()
        }
    }

    fun release() {
        for (i in 0 until mediaPlayers.size()) {
            mediaPlayers.valueAt(i).release()
        }
        mediaPlayers.clear()
    }

    private fun releaseMediaPlayer(soundResId: Int) {
        mediaPlayers[soundResId]?.let {
            if (it.isPlaying) it.stop()
            it.release()
            mediaPlayers.remove(soundResId)
        }
    }
}