package com.example.conversaomoedas.classes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Connection {
    fun isDisconnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return true

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return true

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false

            else -> true
        }
    }
}