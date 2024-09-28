package com.magicmex.canfiree.view.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.magicmex.canfiree.databinding.ActivityMainBinding
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.utils.network.NetworkManager.checkStateEthernet
import com.magicmex.canfiree.utils.preference.PreferenceManager
import com.magicmex.canfiree.utils.preference.PreferenceManager.initPrivacy
import com.magicmex.canfiree.view.games.GamesActivity
import com.magicmex.canfiree.view.games.offerwall.LoadingOfferWall
import com.magicmex.canfiree.view.privacy.PrivacyActivity
import com.magicmex.canfiree.view.splash.AdManager.initializeAd
import com.magicmex.canfiree.view.splash.LoadingBarManager.getProgressBarWidth
import com.magicmex.canfiree.view.splash.LoadingBarManager.startLoadingAnimation
import com.magicmex.canfiree.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var loadingOfferWall = LoadingOfferWall()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        initPrivacy(this)
        sharedPreferences = this.getSharedPreferences("MagicMexicanFirePref", Context.MODE_PRIVATE)
        if (checkStateEthernet(this)) {
            animationProgressBar()
            lifecycleScope.launch {
                delay(3000L)
                loadingOfferWall()
            }
        } else {
            sharedPreferences.edit().putBoolean("OfferWallStatus", false).apply()
            navigateTo()
        }
    }

    fun navigateTo() {
        animationProgressBar()
        lifecycleScope.launch {
            delay(15000L)
            checkNavigateToPrivacy()
        }
    }

    private fun animationProgressBar() {
        binding.customBarLoad.lineBar.startLoadingAnimation(
            duration = 15000L,
            maxWidth = this.getProgressBarWidth()
        )
    }

    private fun loadingOfferWall() {
        loadingOfferWall.fetchInterstitialData { result ->
            when (result) {
                is LoadingOfferWall.Result.Success -> {
                    sharedPreferences.edit().putBoolean("OfferWallStatus", true).apply()
                    val intent = Intent(this@MainActivity, GamesActivity::class.java)
                    val gamesList = result.data
                    intent.putParcelableArrayListExtra("games", ArrayList(gamesList))
                    startActivity(intent)
                    finish()
                }

                is LoadingOfferWall.Result.Failure -> handleFailure(result.exception)
            }
        }
    }

    private fun handleFailure(exception: Exception) {
        sharedPreferences.edit().putBoolean("OfferWallStatus", false).apply()
        if (checkStateEthernet(this)) {
            initializeAd(this)
            lifecycleScope.launch {
                delay(5000L)
                if (!isAdShowed()) checkNavigateToPrivacy()
            }
        } else {
            checkNavigateToPrivacy()
        }
    }

    private fun isAdShowed(): Boolean = AdManager.isAdShowed

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

    override fun onDestroy() {
        super.onDestroy()
        loadingOfferWall.cancel()
    }
}