package com.maxim.menutest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class InternetConnectivityManager(
    context: Context,
    private val onConnectivityChanged: (ConnectivityChanged) -> Unit
) {

    private var connectivity = ConnectivityChanged.AVAILABLE

    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (connectivity == ConnectivityChanged.LOST)
                onConnectivityChanged(ConnectivityChanged.AVAILABLE)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            // We can monitor here internet [networkCapabilities]
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectivity = ConnectivityChanged.LOST

            onConnectivityChanged(connectivity)
        }
    }

    fun register() {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

enum class ConnectivityChanged {
    AVAILABLE, LOST
}