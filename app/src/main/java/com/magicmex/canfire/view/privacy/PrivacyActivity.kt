package com.magicmex.canfire.view.privacy

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivityPrivacyBinding
import com.magicmex.canfire.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.view.menu.MenuActivity

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    private lateinit var preferencesApp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        preferencesApp = PreferenceManager.getPreference(this)
        acceptPrivacyPolicy()
    }

    private fun acceptPrivacyPolicy() {
        binding.apply {
            btnAccept.setOnClickListener {
                it.startAnimation(setAnimationClickButton(this@PrivacyActivity))
                preferencesApp.edit().putBoolean("PrivacyStatus", true).apply()
                startActivity(Intent(this@PrivacyActivity, MenuActivity::class.java))
                finish()
            }

            textPrivacyLink.setOnClickListener {
                it.startAnimation(setAnimationClickButton(this@PrivacyActivity))
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.link_privacy))
                    )
                )
            }
        }
    }

    @Deprecated(
        "Deprecated in Java"
    )
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}