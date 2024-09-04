package com.magicmex.canfiree.view.games.kenogame.dialog

import android.content.SharedPreferences
import com.magicmex.canfiree.model.kenogame.HighScoreKeno

object HighScoreKenoManager {

    private const val GAMES_PLAYED_SUFFIX = "_gamesPlayed"
    private const val WINS_SUFFIX = "_wins"
    private const val LOSSES_SUFFIX = "_losses"

    val statsHighScoreKeno = mutableMapOf(
        "Level 1" to HighScoreKeno(),
        "Level 2" to HighScoreKeno(),
        "Level 3" to HighScoreKeno()
    )

    fun saveStatsScoreKenoGame(preferences: SharedPreferences) {
        val editor = preferences.edit()
        statsHighScoreKeno.forEach { (level, stats) ->
            editor.apply {
                putInt(level + GAMES_PLAYED_SUFFIX, stats.countAllGamesPlayed)
                putInt(level + WINS_SUFFIX, stats.countWins)
                putInt(level + LOSSES_SUFFIX, stats.countLosses)
            }
        }
        editor.apply()
    }

    fun loadScoreKenoGame(sharedPref: SharedPreferences) {
        statsHighScoreKeno.forEach { (level, stats) ->
            stats.countAllGamesPlayed = sharedPref.getInt(level + GAMES_PLAYED_SUFFIX, 0)
            stats.countWins = sharedPref.getInt(level + WINS_SUFFIX, 0)
            stats.countLosses = sharedPref.getInt(level + LOSSES_SUFFIX, 0)
        }
    }

    fun resetStatsScoreKenoGame(preferences: SharedPreferences) {
        for ((_, stats) in statsHighScoreKeno) {
            stats.countAllGamesPlayed = 0
            stats.countWins = 0
            stats.countLosses = 0
        }
        saveStatsScoreKenoGame(preferences)
    }
}