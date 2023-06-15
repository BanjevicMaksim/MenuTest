package com.maxim.menutest.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

/**
 * This represents another way we can observe internet connection by
 * intercepting API calls and broadcasting it for everyone who subscribes,
 * or to throw an [NoNetworkException] so we can get that exception handled.
 */
class ConnectivityCheckingInterceptor(
    context: Context
) : Interceptor, ConnectivityManager.NetworkCallback() {

    private var online = false
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var localBroadcastManager = LocalBroadcastManager.getInstance(context)

    init {
        if (Build.VERSION.SDK_INT >= 24) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }
    }

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        if (Build.VERSION.SDK_INT < 24) {
            online = connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

        return if (online) {
            chain.proceed(chain.request())
        } else {
            localBroadcastManager.sendBroadcast(Intent(NO_INTERNET))
            /**
             * We can proceed here and let the request continue while we send broadcast
             * or
             * we can throw an [NoNetworkException] and catch it in the UseCase
             * throw NoNetworkException()
             */
            throw NoNetworkException()
        }
    }

    override fun onCapabilitiesChanged(
        network: Network,
        capabilities: NetworkCapabilities
    ) {
        online = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    companion object {
        const val NO_INTERNET = "NO_INTERNET"
    }
}

class NoNetworkException: IOException()