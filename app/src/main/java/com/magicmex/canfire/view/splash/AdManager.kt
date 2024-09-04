package com.magicmex.canfire.view.splash

import androidx.appcompat.app.AppCompatActivity
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks

object AdManager {
    private var isAdShowed = false
    fun initializeAd(activity: AppCompatActivity) {
        Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
            override fun onInterstitialLoaded(isPrecache: Boolean) {
                if (!isAdShowed) {
                    Appodeal.show(activity, Appodeal.INTERSTITIAL)
                }
            }

            override fun onInterstitialFailedToLoad() {
                (activity as MainActivity).navigateTo()
            }

            override fun onInterstitialShown() {
                isAdShowed = true
            }

            override fun onInterstitialShowFailed() {
                (activity as MainActivity).navigateTo()
            }

            override fun onInterstitialClosed() {
                (activity as MainActivity).checkNavigateToPrivacy()
            }

            override fun onInterstitialClicked() {}

            override fun onInterstitialExpired() {
                Appodeal.cache(activity, Appodeal.INTERSTITIAL)
            }
        })
    }
}