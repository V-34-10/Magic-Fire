package com.magicmex.canfiree.view.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.ActivityMenuBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.view.games.GamesActivity
import com.magicmex.canfiree.view.settings.SettingsActivity

class MenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        controlBarMenuButton()
    }

    private fun controlBarMenuButton() {
        binding.apply {
            setupButton(
                buttonGames,
                GamesActivity::class.java,
                setAnimationClickButton(this@MenuActivity)
            )
            setupButton(
                buttonSettings,
                SettingsActivity::class.java,
                setAnimationClickButton(this@MenuActivity)
            )
            textExit.setOnClickListener {
                it.startAnimation(setAnimationClickButton(this@MenuActivity))
                it.postDelayed(
                    { finishAffinity() },
                    setAnimationClickButton(this@MenuActivity).duration
                )
            }
        }
        binding.buttonPrivacy.setOnClickListener {
            it.startAnimation(setAnimationClickButton(this))
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.link_privacy))
                )
            )
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