package com.magicmex.canfire.view.games.findgame.dialog

import android.content.SharedPreferences
import com.magicmex.canfire.model.HighScoreFindPair

object HighScoreManager {

    val statsHighScore = mutableMapOf(
        "Level 1" to HighScoreFindPair(),
        "Level 2" to HighScoreFindPair(),
        "Level 3" to HighScoreFindPair()
    )

    fun saveStatsScoreFindPairGame(preferences: SharedPreferences) {
        val editor = preferences.edit()
        for ((level, stats) in statsHighScore) {
            editor.putLong("${level}bestTime", stats.bestTime)
            editor.putInt("${level}bestSteps", stats.bestSteps)
        }
        editor.apply()
    }

    fun loadScoreFindPairGame(sharedPref: SharedPreferences) {
        for ((level, stats) in statsHighScore) {
            stats.bestTime = sharedPref.getLong("${level}bestTime", Long.MAX_VALUE)
            stats.bestSteps = sharedPref.getInt("${level}bestSteps", 0)
        }
    }
}