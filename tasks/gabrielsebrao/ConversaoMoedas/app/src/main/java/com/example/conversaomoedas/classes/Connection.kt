package com.example.conversaomoedas.classes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Connection {
    fun isDisconnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false
            else -> true
        }

    }
}