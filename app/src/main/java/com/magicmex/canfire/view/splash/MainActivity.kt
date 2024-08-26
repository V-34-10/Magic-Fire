package com.magicmex.canfire.view.splash

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.magicmex.canfire.databinding.ActivityMainBinding
import com.magicmex.canfire.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        navigateToWelcome()
    }

    private fun getDisplayMetrics(): Int {
        val displayMetrics = resources.displayMetrics
        displayMetrics.widthPixels
        val progressBarWidth = (250 * displayMetrics.density).toInt()
        return progressBarWidth - 10
    }

    private fun navigateToWelcome(){
        loadingBar()
        lifecycleScope.launch {
            delay( 3000L)
            startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun loadingBar() {
        val maxAnimationWidth = getDisplayMetrics()
        val layoutParams = binding.loadingBar.loadingLine.layoutParams

        val animation = ValueAnimator.ofInt(10, maxAnimationWidth).apply {
            this.duration = 3000L
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                binding.loadingBar.loadingLine.layoutParams = layoutParams
            }
        }

        animation.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}