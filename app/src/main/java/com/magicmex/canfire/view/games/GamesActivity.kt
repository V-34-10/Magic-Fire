package com.magicmex.canfire.view.games

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivityGamesBinding
import com.magicmex.canfire.view.level.LevelsActivity

class GamesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGamesBinding.inflate(layoutInflater) }
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        preferences = getSharedPreferences("MagicMexicanFirePref", MODE_PRIVATE)
        choiceGameMenuButton()
    }

    private fun choiceGameMenuButton() {
        val animationButton = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.apply {
            setupGameButton(buttonGameFirst, R.string.button_game_first, animationButton)
            setupGameButton(buttonGameSecond, R.string.button_game_second, animationButton)
        }
    }

    private fun setupGameButton(button: View, gameNameResId: Int, animation: Animation) {
        button.setOnClickListener {
            it.startAnimation(animation)
            preferences.edit().putString("GameName", getString(gameNameResId)).apply()
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