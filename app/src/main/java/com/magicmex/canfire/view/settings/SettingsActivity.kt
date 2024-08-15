package com.magicmex.canfire.view.settings

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.view.settings.VibroController.vibroEmulateDevice
import com.magicmex.canfire.view.settings.VibroController.vibroEmulateOff
import com.magicmex.canfire.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private lateinit var preferences: SharedPreferences
    private val managerMusic by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var defaultMusicVolume: Int = 25
    private var statusVibro: Boolean = false
    private var statusMusic: Boolean = false
    private lateinit var musicController: MusicController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        musicController = MusicController(this)
        preferences = getSharedPreferences("MagicMexicanFirePref", MODE_PRIVATE)
        buttonsControls()
    }

    private fun buttonsControls() {
        statusVibro = preferences.getBoolean("vibroStatus", false)
        statusMusic = preferences.getBoolean("musicStatus", false)
        val animationClick = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        binding.buttonResetScore.setOnClickListener {
            it.startAnimation(animationClick)
            Toast.makeText(applicationContext, "Total reset score", Toast.LENGTH_SHORT).show()
        }
        binding.buttonMusicOn.setOnClickListener {
            it.startAnimation(animationClick)
            onMusic()
            preferences.edit().putBoolean("musicStatus", true).apply()
            vibroMode()
            musicMode()
        }
        binding.buttonMusicOff.setOnClickListener {
            it.startAnimation(animationClick)
            offMusic()
            preferences.edit().putBoolean("musicStatus", false).apply()
            vibroMode()
            musicMode()
        }
        binding.buttonVibroOn.setOnClickListener {
            it.startAnimation(animationClick)
            preferences.edit().putBoolean("vibroStatus", true).apply()
            vibroMode()
        }
        binding.buttonVibroOff.setOnClickListener {
            it.startAnimation(animationClick)
            vibroEmulateOff(this)
            preferences.edit().putBoolean("vibroStatus", false).apply()
            vibroMode()
        }
    }

    private fun onMusic() =
        managerMusic.setStreamVolume(AudioManager.STREAM_MUSIC, defaultMusicVolume, 0)

    private fun offMusic() {
        defaultMusicVolume = managerMusic.getStreamVolume(AudioManager.STREAM_MUSIC)
        managerMusic.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }

    private fun vibroMode() {
        val isVibration = preferences.getBoolean("vibroStatus", false)
        if (isVibration) {
            vibroEmulateDevice(this, 500)
        }
    }

    private fun musicMode() {
        statusMusic = preferences.getBoolean("musicStatus", false)
        if (statusMusic) {
            musicController.apply {
                playSound(R.raw.music_menu, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicController.release()
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
    }
}