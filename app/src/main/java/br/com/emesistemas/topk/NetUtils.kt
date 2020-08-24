package br.com.emesistemas.topk

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

private const val REACHABILITY_SERVER = "https://www.google.com"

object NetUtils {

    fun hasInternetConnected(context: Context): Boolean {
        if (hasNetworkAvailable(context)) {
            try {
                val connection = URL(REACHABILITY_SERVER).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "ConnectionTest")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1800 // configurable
                connection.connect()
                Log.d("NetUtils", "hasInternetConnected: ${(connection.responseCode == 200)}")
                return (connection.responseCode == 200)
            } catch (e: IOException) {
                Log.e("NetUtils", "Error checking internet connection", e)
            }
        } else {
            Log.d("NetUtils", "No network available!")
        }
        Log.d("NetUtils", "hasInternetConnected: false")
        return false
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }
}