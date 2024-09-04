package com.magicmex.canfiree.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfiree.databinding.ActivityWelcomeBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.view.menu.MenuActivity

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
            it.startAnimation(setAnimationClickButton(this))
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