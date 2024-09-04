package com.magicmex.canfiree.utils.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkManager {

    fun checkStateEthernet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }
}