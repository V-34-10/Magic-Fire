package com.magicmex.canfiree.view.games.findgame.dialog

import android.content.SharedPreferences
import com.magicmex.canfiree.model.findgame.HighScoreFindPair

object HighScoreFindPairManager {

    private val levels = listOf("Level 1", "Level 2", "Level 3")

    val statsHighScoreFindPair = levels.associateWith { HighScoreFindPair() }.toMutableMap()

    fun saveStatsScoreFindPairGame(preferences: SharedPreferences) {
        preferences.edit().apply {
            statsHighScoreFindPair.forEach { (level, stats) ->
                putLong(preferenceKey(level, "bestTime"), stats.bestTime)
                putInt(preferenceKey(level, "bestSteps"), stats.bestSteps)
            }
        }.apply()
    }

    fun loadScoreFindPairGame(sharedPref: SharedPreferences) {
        statsHighScoreFindPair.forEach { (level, stats) ->
            stats.bestTime = sharedPref.getLong(preferenceKey(level, "bestTime"), Long.MAX_VALUE)
            stats.bestSteps = sharedPref.getInt(preferenceKey(level, "bestSteps"), 0)
        }
    }

    fun resetStatsScoreFindPairGame(preferences: SharedPreferences) {
        statsHighScoreFindPair.values.forEach { stats ->
            stats.reset()
        }
        saveStatsScoreFindPairGame(preferences)
    }

    private fun preferenceKey(level: String, key: String): String {
        return "${level}_$key"
    }
}

fun HighScoreFindPair.reset() {
    bestTime = Long.MAX_VALUE
    bestSteps = 0
}