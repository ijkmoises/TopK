package br.com.emesistemas.topk

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

private const val REACHABILITY_SERVER = "https://www.google.com"

object NetUtils {

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }
}