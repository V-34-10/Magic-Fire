package com.magicmex.canfiree.utils.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.magicmex.canfiree.R

object PreferenceManager {
    var selectedLevel: String = ""
        private set

    var gameName: String = ""
        private set

    var vibroStatus: Boolean = false
        private set

    var musicStatus: Boolean = false
        private set

    var privacyStatus: Boolean = false
        private set

    fun initLevelGame(context: Context) {
        selectedLevel = getPreference(context).getString("LevelGame", "") ?: ""
    }

    fun setLevelGame(context: Context, gameNameResId: Int) =
        getPreference(context).edit().putString("LevelGame", context.getString(gameNameResId))
            .apply()

    fun initGameName(context: Context) {
        gameName = getPreference(context).getString(
            "GameName",
            context.getString(R.string.button_game_first)
        ) ?: ""
    }

    fun setGameName(context: Context, gameNameResId: Int) =
        getPreference(context).edit().putString("GameName", context.getString(gameNameResId))
            .apply()

    fun initPrivacy(context: Context) {
        privacyStatus = getPreference(context).getBoolean("PrivacyStatus", false)
    }

    fun setPrivacyTrue(context: Context) =
        getPreference(context).edit().putBoolean("PrivacyStatus", true).apply()

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