package com.magicmex.canfire.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.magicmex.canfire.databinding.ActivityMainBinding
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.network.NetworkManager.checkStateEthernet
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.utils.preference.PreferenceManager.initPrivacy
import com.magicmex.canfire.view.privacy.PrivacyActivity
import com.magicmex.canfire.view.splash.AdManager.initializeAd
import com.magicmex.canfire.view.splash.LoadingBarManager.getProgressBarWidth
import com.magicmex.canfire.view.splash.LoadingBarManager.startLoadingAnimation
import com.magicmex.canfire.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        initPrivacy(this)
        if (checkStateEthernet(this)) {
            initializeAd(this)
        } else {
            navigateTo()
        }
    }

    fun navigateTo() {
        binding.customBarLoad.lineBar.startLoadingAnimation(
            duration = 3000L,
            maxWidth = this.getProgressBarWidth()
        )
        lifecycleScope.launch {
            delay(3000L)
            checkNavigateToPrivacy()
        }
    }

    fun checkNavigateToPrivacy() {
        val nextActivity = if (PreferenceManager.privacyStatus) {
            WelcomeActivity::class.java
        } else {
            PrivacyActivity::class.java
        }
        startActivity(Intent(this@MainActivity, nextActivity))
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}