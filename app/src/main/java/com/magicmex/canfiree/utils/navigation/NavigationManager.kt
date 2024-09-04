package com.magicmex.canfiree.utils.navigation

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View

object NavigationManager {
    private fun isNavigationBarHiddenRequired(activity: Activity): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && activity.resources.configuration.navigation == Configuration.NAVIGATION_NONAV

    fun setNavigationBarVisibility(activity: Activity) {
        if (isNavigationBarHiddenRequired(activity)) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}