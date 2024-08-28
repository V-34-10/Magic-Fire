package com.magicmex.canfire.view.welcome

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivityWelcomeBinding
import com.magicmex.canfire.view.menu.MenuActivity
import com.magicmex.canfire.view.navigation.NavigationManager

class WelcomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        navigateToMenu()
    }

    private fun navigateToMenu() {
        binding.buttonNext.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation))
            startActivity(Intent(this@WelcomeActivity, MenuActivity::class.java))
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