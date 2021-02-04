package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.TvApplication
import com.raphaelbgr.tvapp.mycryptopricetvapp.model.BitcoinPrices


class LocalDataSource : DataSource {
    private val context = TvApplication.getAppContext()
    private val cacheKey = "last_result_cache"

    var sharedPreferences: SharedPreferences? =
        context?.getSharedPreferences(context.getString(R.string.pref_key), Context.MODE_PRIVATE)

    override fun getBitcoinPrices(): BitcoinPrices? {
        return Gson().fromJson(
            sharedPreferences?.getString(cacheKey, null),
            BitcoinPrices::class.java
        )
    }

    override fun saveBitcoinPrices(btc: BitcoinPrices?) {
        if (btc != null) {
            sharedPreferences?.edit()?.putString(cacheKey, Gson().toJson(btc))?.apply()
        }
    }

}