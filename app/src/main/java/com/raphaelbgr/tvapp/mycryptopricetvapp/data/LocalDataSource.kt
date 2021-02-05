package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.TvApplication
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice


class LocalDataSource : DataSource {
    private val context = TvApplication.getAppContext()
    private val cacheKeyBtc = "last_btc_result_cache"
    private val cacheKeyEth = "last_eth_result_cache"
    private val dollarToBrlExchange = "dollar_to_brl_exchange"

    var sharedPreferences: SharedPreferences? =
        context?.getSharedPreferences(context.getString(R.string.pref_key), Context.MODE_PRIVATE)

    override fun getBitcoinPrices(): CoinPrice? {
        return Gson().fromJson(
            sharedPreferences?.getString(cacheKeyBtc, null),
            CoinPrice::class.java
        )
    }

    override fun getEtherumPrices(): CoinPrice? {
        return Gson().fromJson(
            sharedPreferences?.getString(cacheKeyEth, null),
            CoinPrice::class.java
        )
    }

    override fun saveBitcoinPrices(btc: CoinPrice?) {
        if (btc != null) {
            sharedPreferences?.edit()?.putString(cacheKeyBtc, Gson().toJson(btc))?.apply()
        }
    }

    override fun saveEtherumPrices(eth: CoinPrice?) {
        if (eth != null) {
            sharedPreferences?.edit()?.putString(cacheKeyEth, Gson().toJson(eth))?.apply()
        }
    }

    override fun saveBrlToDollarExchangeRate(dollarPriceToBrl: Double?) {
        if (dollarPriceToBrl != null) {
            sharedPreferences?.edit()?.putFloat(dollarToBrlExchange, dollarPriceToBrl.toFloat())
                ?.apply()
        }
    }

    override fun getBrlToDollarExchangeRate(): Double? {
        return sharedPreferences?.getFloat(dollarToBrlExchange, 0.0F)?.toDouble()
    }
}