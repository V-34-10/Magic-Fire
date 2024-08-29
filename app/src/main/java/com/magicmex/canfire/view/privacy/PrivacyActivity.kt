package com.magicmex.canfire.view.privacy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivityPrivacyBinding
import com.magicmex.canfire.view.menu.MenuActivity
import com.magicmex.canfire.utils.navigation.NavigationManager

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    private var linkPrivacy: String = getString(R.string.link_privacy)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        acceptPrivacyPolicy()
    }

    private fun acceptPrivacyPolicy() {
        val animationClick = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.apply {
            btnAccept.setOnClickListener {
                it.startAnimation(animationClick)
                startActivity(Intent(this@PrivacyActivity, MenuActivity::class.java))
                finish()
            }

            textPrivacyLink.setOnClickListener {
                it.startAnimation(animationClick)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(linkPrivacy)))
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