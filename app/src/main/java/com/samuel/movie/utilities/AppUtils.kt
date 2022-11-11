package com.samuel.movie.utilities

import android.content.Context
import android.net.ConnectivityManager
import java.text.NumberFormat
import java.util.*

object AppUtils {

    //Simple method to check if the device has an active internet connection
    fun hasInternet(context: Context): Boolean {

        val nInfo = (context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }

    @JvmStatic
    fun formatCurrency(amount: Long): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
    }
}