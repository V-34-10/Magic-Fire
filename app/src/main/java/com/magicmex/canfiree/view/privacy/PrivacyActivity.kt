package com.magicmex.canfiree.view.privacy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.ActivityPrivacyBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.utils.preference.PreferenceManager.initPrivacy
import com.magicmex.canfiree.utils.preference.PreferenceManager.setPrivacyTrue
import com.magicmex.canfiree.view.menu.MenuActivity

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        initPrivacy(this)
        acceptPrivacyPolicy()
    }

    private fun acceptPrivacyPolicy() {
        binding.apply {
            btnAccept.setOnClickListener {
                it.startAnimation(setAnimationClickButton(this@PrivacyActivity))
                setPrivacyTrue(this@PrivacyActivity)
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