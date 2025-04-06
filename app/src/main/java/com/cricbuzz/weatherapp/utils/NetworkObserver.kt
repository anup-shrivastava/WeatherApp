package com.cricbuzz.weatherapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.cricbuzz.weatherapp.utils.WeatherConstants.isWifiConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _networkStatus = MutableStateFlow(isInternetAvailable(context))
    val networkStatus = _networkStatus.asStateFlow()

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            _networkStatus.value = isInternetAvailable(context!!)
            Timber.tag("NetworkCheck").d("Network Status: ${_networkStatus.value}")
        }
    }

    init {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, intentFilter)
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(networkReceiver)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val activeNetwork = connectivityManager.getNetworkCapabilities(network)
        recheckWifiStatus(connectivityManager)
        return activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true
    }

    private fun recheckWifiStatus(connectivityManager: ConnectivityManager) {
        try {
            val isConnected: Boolean? =
                connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected
            isWifiConnected = isConnected != null && isConnected
            Log.e("NetworkCheck", "wifiConnected  : $isConnected")

        } catch (ex: Exception) {
            ex.printStackTrace()
            isWifiConnected = false
        }
    }
}

