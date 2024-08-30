package com.magicmex.canfire.utils.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

object PreferenceManager {
    var selectedLevel: String = ""
        private set

    var vibroStatus: Boolean = false
        private set

    var musicStatus: Boolean = false
        private set

    fun initLevelGame(context: Context) {
        selectedLevel = getPreference(context).getString("LevelGame", "") ?: ""
    }

    fun initSettings(context: Context) {
        vibroStatus = getPreference(context).getBoolean("vibroStatus", false)
        musicStatus = getPreference(context).getBoolean("musicStatus", false)
    }

    fun setSettingsMusicTrue(context: Context) =
        getPreference(context).edit().putBoolean("musicStatus", true).apply()

    fun setSettingsVibroTrue(context: Context) =
        getPreference(context).edit().putBoolean("vibroStatus", true).apply()

    fun setSettingsMusicFalse(context: Context) =
        getPreference(context).edit().putBoolean("musicStatus", false).apply()

    fun setSettingsVibroFalse(context: Context) =
        getPreference(context).edit().putBoolean("vibroStatus", false).apply()

    fun getPreference(context: Context): SharedPreferences =
        context.getSharedPreferences("MagicMexicanFirePref", AppCompatActivity.MODE_PRIVATE)
}