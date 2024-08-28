package com.magicmex.canfire.view.games

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.ActivitySceneBinding
import com.magicmex.canfire.view.games.findgame.FindPairGameFragment
import com.magicmex.canfire.view.games.findgame.manager.GameSettings
import com.magicmex.canfire.view.games.kenogame.KenoGameFragment
import com.magicmex.canfire.view.level.LevelsActivity
import com.magicmex.canfire.view.navigation.NavigationManager

class SceneActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySceneBinding.inflate(layoutInflater) }
    private lateinit var preferencesApp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        preferencesApp = GameSettings.getPreference(this)
        initGameFragment()
    }

    private fun initGameFragment() = replaceFragment(
        createFragmentGames(
            preferencesApp.getString(
                "GameName",
                getString(R.string.button_game_first)
            )
        ), R.id.container_games
    )

    private fun createFragmentGames(game: String?): Fragment {
        return when (game) {
            getString(R.string.button_game_second) -> FindPairGameFragment()
            else -> KenoGameFragment()
        }
    }

    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.commit {
            replace(containerId, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (!handleBackStack()) {
            navigateToMenuActivity()
        }
    }

    private fun handleBackStack(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }

    private fun navigateToMenuActivity() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        startActivity(Intent(this, LevelsActivity::class.java))
        finish()
    }
}