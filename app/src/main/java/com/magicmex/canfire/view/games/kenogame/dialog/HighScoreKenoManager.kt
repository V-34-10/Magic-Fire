package com.magicmex.canfire.view.games.kenogame.dialog

import android.content.SharedPreferences
import com.magicmex.canfire.model.kenogame.HighScoreKeno

object HighScoreKenoManager {

    val statsHighScoreKeno = mutableMapOf(
        "Level 1" to HighScoreKeno(),
        "Level 2" to HighScoreKeno(),
        "Level 3" to HighScoreKeno()
    )

    fun saveStatsScoreKenoGame(preferences: SharedPreferences) {
        val editor = preferences.edit()
        for ((level, stats) in statsHighScoreKeno) {
            editor.putInt("${level}_gamesPlayed", stats.countAllGamesPlayed)
            editor.putInt("${level}_wins", stats.countWins)
            editor.putInt("${level}_losses", stats.countLosses)
        }
        editor.apply()
    }

    fun loadScoreKenoGame(sharedPref: SharedPreferences) {
        for ((level, stats) in statsHighScoreKeno) {
            stats.countAllGamesPlayed = sharedPref.getInt("${level}_gamesPlayed", 0)
            stats.countWins = sharedPref.getInt("${level}_wins", 0)
            stats.countLosses = sharedPref.getInt("${level}_losses", 0)
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