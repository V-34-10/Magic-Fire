package com.magicmex.canfire

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        controlBarMenuButton()
    }

    private fun controlBarMenuButton() {
        val animationButton = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.apply {
            setupButton(buttonGames, GamesActivity::class.java, animationButton)
            setupButton(buttonSettings, SettingsActivity::class.java, animationButton)
            setupButton(buttonPrivacy, PrivacyActivity::class.java, animationButton)
            textExit.setOnClickListener {
                it.startAnimation(animationButton)
                it.postDelayed({ finishAffinity() }, animationButton.duration)
            }
        }
    }

    private fun <T : AppCompatActivity> setupButton(
        button: View,
        activityClass: Class<T>,
        animation: Animation
    ) {
        button.setOnClickListener {
            it.startAnimation(animation)
            startActivity(Intent(this@MenuActivity, activityClass))
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