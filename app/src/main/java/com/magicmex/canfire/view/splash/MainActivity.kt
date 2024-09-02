package com.magicmex.canfire.view.splash

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.magicmex.canfire.databinding.ActivityMainBinding
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.view.border.BorderBanner
import com.magicmex.canfire.view.privacy.PrivacyActivity
import com.magicmex.canfire.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var preferencesApp: SharedPreferences
    private lateinit var borderBanner: BorderBanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        preferencesApp = PreferenceManager.getPreference(this)
        borderBanner = BorderBanner(this, binding)
        navigateTo()
    }

    private fun getDisplayMetrics(): Int {
        val displayMetrics = resources.displayMetrics
        displayMetrics.widthPixels
        val progressBarWidth = (350 * displayMetrics.density).toInt()
        return progressBarWidth - 10
    }

    private fun navigateTo() {
        loadingBar()
        lifecycleScope.launch {
            delay(3000L)
            /*if (!checkStateEthernet()) {
                checkNavigateToPrivacy()
            } else {
                initBorderBanner()
            }*/
            /*startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
            finish()*/
            checkNavigateToPrivacy()
        }
    }

    private fun checkNavigateToPrivacy() {
        if (!preferencesApp.getBoolean("PrivacyStatus", false)) {
            startActivity(Intent(this@MainActivity, PrivacyActivity::class.java))

        } else {
            startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
        }
        finish()
    }

    @SuppressLint("ObsoleteSdkInt", "ServiceCast")
    private fun checkStateEthernet(): Boolean {
        val activeNetworkInfo =
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return activeNetworkInfo != null && (activeNetworkInfo.isConnected || activeNetworkInfo.isConnectedOrConnecting)
    }

    private fun initBorderBanner() {
        val hasUserData =
            preferencesApp.contains("cookies") && preferencesApp.contains("userAgent")
        if (hasUserData) {
            val cookies = preferencesApp.getString("cookies", "")
            val userAgent = preferencesApp.getString("userAgent", "")
            val actionUrl = preferencesApp.getString("actionUrl", "")
            val sourceUrl = preferencesApp.getString("sourceUrl", "")
            borderBanner.loadImage(
                sourceUrl.toString(),
                actionUrl.toString(),
                cookies,
                userAgent
            )
        } else {
            borderBanner.fetchInterstitialData()
        }
    }

    private fun loadingBar() {
        val maxAnimationWidth = getDisplayMetrics()
        val layoutParams = binding.customBarLoad.lineBar.layoutParams

        val animation = ValueAnimator.ofInt(10, maxAnimationWidth).apply {
            this.duration = 3000L
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                binding.customBarLoad.lineBar.layoutParams = layoutParams
            }
        }

        animation.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        borderBanner.cancel()
    }
}