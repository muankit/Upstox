package com.upstox.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

inline fun <T> Flow<T>.collectLatest(
    scope: CoroutineScope,
    crossinline action: suspend (value: T) -> Unit
): Job {
    return scope.launch { collectLatest { action(it) } }
}

class NetworkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true
        }
    }
}

fun Double?.currencyFormat(): String {
    return if (this != null && this < 0) {
        String.format(Locale.getDefault(), "-₹%,.2f", -this)
    } else {
        String.format(Locale.getDefault(), "₹%,.2f", this ?: 0.0)
    }
}

fun Double?.format(): String {
    return String.format(Locale.getDefault(), "%,.2f", this)
}