package com.magicmex.canfire

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPrivacyBinding.inflate(layoutInflater) }
    private var linkPrivacy: String = "https://www.google.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
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