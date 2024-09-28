package com.magicmex.canfiree.view.games

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.magicmex.canfiree.R
import com.magicmex.canfiree.databinding.ActivityGamesBinding
import com.magicmex.canfiree.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfiree.utils.navigation.NavigationManager
import com.magicmex.canfiree.utils.preference.PreferenceManager.setGameName
import com.magicmex.canfiree.view.games.offerwall.adapter.GameAdapter
import com.magicmex.canfiree.view.games.offerwall.model.Game
import com.magicmex.canfiree.view.level.LevelsActivity

class GamesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGamesBinding.inflate(layoutInflater) }
    private lateinit var adapter: GameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationManager.setNavigationBarVisibility(this)
        checkOfferWallStatus()
        choiceGameMenuButton()
    }

    private fun checkOfferWallStatus() {
        if (this.getSharedPreferences("MagicMexicanFirePref", Context.MODE_PRIVATE)
                .getBoolean("OfferWallStatus", false)
        ) {
            binding.listGames.visibility = View.VISIBLE
            binding.listGames.layoutManager = LinearLayoutManager(this)
            adapter = GameAdapter(this::onGameClick)
            binding.listGames.adapter = adapter

            val games = intent.getParcelableArrayListExtra<Game>("games") ?: emptyList()
            Log.d("GamesActivity", "Games received:")
            for (game in games) {
                Log.d("GamesActivity", "inx: ${game.moduleCode}, title: ${game.levelTitle}, bgUrl: ${game.visualFile}, fgUrl: ${game.auxImage}, play: ${game.activateText}")
            }
            Log.d("GamesActivity", "Games received: ${games.size}")
            Log.d("GamesActivity", "Intent extras: ${intent.extras}")
            adapter.updateGames(games)

        } else {
            binding.buttonGameFirst.visibility = View.VISIBLE
            binding.buttonGameSecond.visibility = View.VISIBLE
        }
    }

    private fun onGameClick(game: Game) {
        if (game.levelTitle.startsWith("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(game.levelTitle))
            startActivity(intent)
        } else {
            Toast.makeText(this, "Opening game ${game.moduleCode}", Toast.LENGTH_SHORT).show()
            when (game.moduleCode) {
                0 -> {
                    setGameName(this, R.string.button_game_first)
                }

                1 -> {
                    setGameName(this, R.string.button_game_second)
                }
            }
            startActivity(Intent(this@GamesActivity, LevelsActivity::class.java))
        }
    }

    private fun choiceGameMenuButton() {
        binding.apply {
            setupGameButton(
                buttonGameFirst,
                R.string.button_game_first,
                setAnimationClickButton(this@GamesActivity)
            )
            setupGameButton(
                buttonGameSecond,
                R.string.button_game_second,
                setAnimationClickButton(this@GamesActivity)
            )
        }
    }

    private fun setupGameButton(button: View, gameNameResId: Int, animation: Animation) {
        button.setOnClickListener {
            it.startAnimation(animation)
            setGameName(this, gameNameResId)
            startActivity(Intent(this@GamesActivity, LevelsActivity::class.java))
        }
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
    }
}