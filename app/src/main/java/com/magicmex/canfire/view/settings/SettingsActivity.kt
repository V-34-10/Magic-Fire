package com.magicmex.canfire.view.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivitySettingsBinding
import com.magicmex.canfire.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.utils.preference.PreferenceManager.initSettings
import com.magicmex.canfire.utils.preference.PreferenceManager.setSettingsMusicFalse
import com.magicmex.canfire.utils.preference.PreferenceManager.setSettingsMusicTrue
import com.magicmex.canfire.utils.preference.PreferenceManager.setSettingsVibroFalse
import com.magicmex.canfire.utils.preference.PreferenceManager.setSettingsVibroTrue
import com.magicmex.canfire.view.games.findgame.dialog.HighScoreFindPairManager.resetStatsScoreFindPairGame
import com.magicmex.canfire.view.games.kenogame.dialog.HighScoreKenoManager.resetStatsScoreKenoGame
import com.magicmex.canfire.view.settings.music.MusicController
import com.magicmex.canfire.view.settings.music.MusicVolumeManager
import com.magicmex.canfire.view.settings.vibro.VibroController.vibroEmulateOff
import com.magicmex.canfire.view.settings.vibro.VibroStart.vibroMode

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private lateinit var musicController: MusicController
    private lateinit var musicVolume: MusicVolumeManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        musicController = MusicController(this)
        musicVolume = MusicVolumeManager(this)
        initSettings(this)
        buttonsControls()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buttonsControls() {
        val onClickListener = View.OnClickListener { view ->
            view.startAnimation(setAnimationClickButton(this))
            when (view.id) {
                R.id.button_reset_score -> {
                    Toast.makeText(applicationContext, R.string.reset_message, Toast.LENGTH_SHORT)
                        .show()
                    resetStatsScoreFindPairGame(PreferenceManager.getPreference(this))
                    resetStatsScoreKenoGame(PreferenceManager.getPreference(this))
                }
                R.id.button_music_on -> {
                    musicVolume.setMusicVolume()
                    setSettingsMusicTrue(this)
                }
                R.id.button_music_off -> {
                    musicVolume.offMusicVolume()
                    setSettingsMusicFalse(this)
                }
                R.id.button_vibro_on -> {
                    setSettingsVibroTrue(this)
                    initSettings(this)
                }
                R.id.button_vibro_off -> {
                    vibroEmulateOff(this)
                    setSettingsVibroFalse(this)
                    initSettings(this)
                }
            }
            vibroMode(this)
        }

        binding.buttonResetScore.setOnClickListener(onClickListener)
        binding.buttonMusicOn.setOnClickListener(onClickListener)
        binding.buttonMusicOff.setOnClickListener(onClickListener)
        binding.buttonVibroOn.setOnClickListener(onClickListener)
        binding.buttonVibroOff.setOnClickListener(onClickListener)
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