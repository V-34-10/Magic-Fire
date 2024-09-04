package com.magicmex.canfiree.view.level

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.ActivityLevelsBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.utils.preference.PreferenceManager.setLevelGame
import com.magicmex.canfiree.view.scene.SceneActivity

class LevelsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLevelsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        choiceLevelsGameButton()
    }

    private fun choiceLevelsGameButton() {
        binding.apply {
            setupLevelsGameButton(
                buttonLevelFirst,
                R.string.button_level_first,
                setAnimationClickButton(this@LevelsActivity)
            )
            setupLevelsGameButton(
                buttonLevelSecond,
                R.string.button_level_second,
                setAnimationClickButton(this@LevelsActivity)
            )
            setupLevelsGameButton(
                buttonLevelThree,
                R.string.button_level_three,
                setAnimationClickButton(this@LevelsActivity)
            )
            textExit.setOnClickListener {
                it.startAnimation(setAnimationClickButton(this@LevelsActivity))
                it.postDelayed(
                    { finishAffinity() },
                    setAnimationClickButton(this@LevelsActivity).duration
                )
            }
        }
    }

    private fun setupLevelsGameButton(button: View, gameNameResId: Int, animation: Animation) {
        button.setOnClickListener {
            it.startAnimation(animation)
            setLevelGame(this, gameNameResId)
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