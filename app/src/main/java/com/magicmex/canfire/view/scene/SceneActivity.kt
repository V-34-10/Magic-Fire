package com.magicmex.canfire.view.scene

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivitySceneBinding
import com.magicmex.canfire.utils.navigation.NavigationManager
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.utils.preference.PreferenceManager.initGameName
import com.magicmex.canfire.view.level.LevelsActivity

class SceneActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySceneBinding.inflate(layoutInflater) }
    private lateinit var fragmentManagerHelper: FragmentManagerHelper
    private lateinit var gameFragmentFactory: GameFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        fragmentManagerHelper = FragmentManagerHelper(this)
        gameFragmentFactory = GameFragmentFactory(this)
        initGameName(this)
        initGameFragment()
    }

    private fun initGameFragment() {
        val fragment = gameFragmentFactory.createFragment(PreferenceManager.gameName)
        fragmentManagerHelper.replaceFragment(fragment, R.id.container_games)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!fragmentManagerHelper.handleBackStack()) {
            navigateToMenuActivity()
        } else {
            super.onBackPressed()
        }
    }

    private fun navigateToMenuActivity() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        startActivity(Intent(this, LevelsActivity::class.java))
        finish()
    }
}