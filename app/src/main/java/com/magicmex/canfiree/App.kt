package com.magicmex.canfiree

import android.app.Application
import com.appodeal.ads.Appodeal

class App : Application() {
    private val appKey = "54a2c350801372881d0ff96e40b2536f964559b5526ed38a"
    override fun onCreate() {
        super.onCreate()
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL)
    }
}