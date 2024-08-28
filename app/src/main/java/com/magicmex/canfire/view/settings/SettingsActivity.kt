package com.magicmex.canfire.view.settings

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivitySettingsBinding
import com.magicmex.canfire.view.games.findgame.dialog.HighScoreFindPairManager.resetStatsScoreFindPairGame
import com.magicmex.canfire.view.games.kenogame.dialog.HighScoreKenoManager.resetStatsScoreKenoGame
import com.magicmex.canfire.view.navigation.NavigationManager
import com.magicmex.canfire.view.settings.VibroController.vibroEmulateDevice
import com.magicmex.canfire.view.settings.VibroController.vibroEmulateOff

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private lateinit var preferences: SharedPreferences
    private val managerMusic by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var defaultMusicVolume: Int = 25
    private var statusVibro: Boolean = false
    private var statusMusic: Boolean = false
    private lateinit var musicController: MusicController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        musicController = MusicController(this)
        preferences = getSharedPreferences("MagicMexicanFirePref", MODE_PRIVATE)
        buttonsControls()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buttonsControls() {
        statusVibro = preferences.getBoolean("vibroStatus", false)
        statusMusic = preferences.getBoolean("musicStatus", false)
        val animationClick = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        binding.buttonResetScore.setOnClickListener {
            it.startAnimation(animationClick)
            Toast.makeText(applicationContext, R.string.reset_message, Toast.LENGTH_SHORT).show()
            resetStatsScoreFindPairGame(preferences)
            resetStatsScoreKenoGame(preferences)
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

    @RequiresApi(Build.VERSION_CODES.O)
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