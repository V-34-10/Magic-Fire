package com.magicmex.canfire.utils.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object PreferenceManager {
    private lateinit var preferences: SharedPreferences

    var selectedLevel: String = ""
        private set

    fun init(context: Context) {
        preferences = getPreference(context)
        selectedLevel = preferences.getString("LevelGame", "") ?: ""
    }

    fun getPreference(context: Context): SharedPreferences =
        context.getSharedPreferences("MagicMexicanFirePref", AppCompatActivity.MODE_PRIVATE)
}