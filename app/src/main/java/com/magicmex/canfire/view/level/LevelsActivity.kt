package com.magicmex.canfire.view.level

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.view.games.SceneActivity
import com.magicmex.canfire.databinding.ActivityLevelsBinding
import com.magicmex.canfire.view.navigation.NavigationManager

class LevelsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLevelsBinding.inflate(layoutInflater) }
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        preferences = getSharedPreferences("MagicMexicanFirePref", MODE_PRIVATE)
        choiceLevelsGameButton()
    }

    private fun choiceLevelsGameButton() {
        val animationButton = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.apply {
            setupLevelsGameButton(buttonLevelFirst, R.string.button_level_first, animationButton)
            setupLevelsGameButton(buttonLevelSecond, R.string.button_level_second, animationButton)
            setupLevelsGameButton(buttonLevelThree, R.string.button_level_three, animationButton)
            textExit.setOnClickListener {
                it.startAnimation(animationButton)
                it.postDelayed({ finishAffinity() }, animationButton.duration)
            }
        }
    }

    private fun setupLevelsGameButton(button: View, gameNameResId: Int, animation: Animation) {
        button.setOnClickListener {
            it.startAnimation(animation)
            preferences.edit().putString("LevelGame", getString(gameNameResId)).apply()
            startActivity(Intent(this@LevelsActivity, SceneActivity::class.java))
            finish()
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