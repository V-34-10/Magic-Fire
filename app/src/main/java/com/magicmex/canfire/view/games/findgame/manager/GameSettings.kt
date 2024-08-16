package com.magicmex.canfire.view.games.findgame.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object GameSettings {
    private lateinit var preferences: SharedPreferences

    var selectedLevel: String = ""
        private set

    fun init(context: Context) {
        preferences = getPreference(context)
        selectedLevel = preferences.getString("LevelGame", "") ?: ""
    }

    fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "MagicMexicanFirePref",
            AppCompatActivity.MODE_PRIVATE
        )
    }
}