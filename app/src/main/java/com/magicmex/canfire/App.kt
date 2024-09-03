package com.magicmex.canfire

import android.app.Application
import com.appodeal.ads.Appodeal

class App : Application() {
    private val appKey = "9a6df8362de4a5e6deb688ee2110a3e380e788aa834c4170"
    override fun onCreate() {
        super.onCreate()
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL)
    }
}