package com.magicmex.canfire.view.games

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivityGamesBinding
import com.magicmex.canfire.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.view.level.LevelsActivity

class GamesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGamesBinding.inflate(layoutInflater) }
    private lateinit var preferencesApp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        preferencesApp = PreferenceManager.getPreference(this)
        choiceGameMenuButton()
    }

    private fun choiceGameMenuButton() {
        binding.apply {
            setupGameButton(
                buttonGameFirst,
                R.string.button_game_first,
                setAnimationClickButton(this@GamesActivity)
            )
            setupGameButton(
                buttonGameSecond,
                R.string.button_game_second,
                setAnimationClickButton(this@GamesActivity)
            )
        }
    }

    private fun setupGameButton(button: View, gameNameResId: Int, animation: Animation) {
        button.setOnClickListener {
            it.startAnimation(animation)
            preferencesApp.edit().putString("GameName", getString(gameNameResId)).apply()
            startActivity(Intent(this@GamesActivity, LevelsActivity::class.java))
        }
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
    }
}