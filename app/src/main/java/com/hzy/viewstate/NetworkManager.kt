package com.hzy.viewstate

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 *
 * @author: ziye_huang
 * @date: 2019/3/25
 */
class NetworkManager {
    companion object {
        @Suppress("DEPRECATION")
        @SuppressLint("MissingPermission")
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (null != networkInfo && networkInfo.isConnected) {
                if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            return false
        }
    }
}