package com.magicmex.canfire.view.games.findgame.dialog

import android.content.SharedPreferences
import com.magicmex.canfire.model.findgame.HighScoreFindPair

object HighScoreFindPairManager {

    val statsHighScoreFindPair = mutableMapOf(
        "Level 1" to HighScoreFindPair(),
        "Level 2" to HighScoreFindPair(),
        "Level 3" to HighScoreFindPair()
    )

    fun saveStatsScoreFindPairGame(preferences: SharedPreferences) {
        val editor = preferences.edit()
        for ((level, stats) in statsHighScoreFindPair) {
            editor.putLong("${level}bestTime", stats.bestTime)
            editor.putInt("${level}bestSteps", stats.bestSteps)
        }
        editor.apply()
    }

    fun loadScoreFindPairGame(sharedPref: SharedPreferences) {
        for ((level, stats) in statsHighScoreFindPair) {
            stats.bestTime = sharedPref.getLong("${level}bestTime", Long.MAX_VALUE)
            stats.bestSteps = sharedPref.getInt("${level}bestSteps", 0)
        }
    }

    fun resetStatsScoreFindPairGame(preferences: SharedPreferences) {
        for ((_, stats) in statsHighScoreFindPair) {
            stats.bestTime = 0
            stats.bestSteps = 0
        }
        saveStatsScoreFindPairGame(preferences)
    }
}