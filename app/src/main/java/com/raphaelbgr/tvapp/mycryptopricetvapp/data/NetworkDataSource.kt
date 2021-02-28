package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import com.google.gson.Gson
import com.raphaelbgr.tvapp.mycryptopricetvapp.TvApplication
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.BRL
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.USD
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class NetworkDataSource : DataSource {

    private val tag = NetworkDataSource::class.java.simpleName
    private val urlProvider = TvApplication.urlProvider

    override fun getBrlToDollarExchangeRate(): Double {
        val json = getRawDataFromNetwork(urlProvider.usdToBrlUrl())
        val data = Gson().fromJson(json, CoinPrice::class.java)
        data?.lastUpdated = Date()
        data?.calculateBrlPriceToDollar()
        Timber.d(data?.toString())
        return data.dollarPriceToBrl
    }

    override fun getBitcoinPrices(): CoinPrice? {
        val brlUsdRate = LocalDataSource().getBrlToDollarExchangeRate() ?: 0.0
        val rawData =
            getRawDataFromNetwork(urlProvider.btcUrl())
        return if (rawData != null) {
            val json = JSONObject(rawData)
            val data = CoinPrice(
                BRL(json.getDouble("last_trade_price") * brlUsdRate),
                USD(json.getDouble("last_trade_price")),
                Date(),
                LocalDataSource().getBrlToDollarExchangeRate() ?: 0.0
            )
            Timber.d(data.toString())
            data
        } else null
    }

    override fun getEtherumPrices(): CoinPrice? {
        val brlUsdRate = LocalDataSource().getBrlToDollarExchangeRate() ?: 0.0
        val rawData =
            getRawDataFromNetwork(urlString = urlProvider.ethUrl())
        return if (rawData != null) {
            val json = JSONObject(rawData)
            val data = CoinPrice(
                BRL(json.getDouble("last_trade_price") * brlUsdRate),
                USD(json.getDouble("last_trade_price")),
                Date(),
                brlUsdRate
            )
            Timber.d(data.toString())
            data
        } else null
    }

    private fun getRawDataFromNetwork(urlString: String): String? {
        var reader: BufferedReader? = null
        val url = URL(urlString)
        val urlConnection = url.openConnection() as HttpsURLConnection
        try {
            reader = BufferedReader(
                InputStreamReader(
                    urlConnection.inputStream,
                    "utf-8"
                )
            )
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            return sb.toString()
        } catch (e: Exception) {
            return null
        } finally {
            urlConnection.disconnect()
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }
        }
    }

    override fun saveBrlToDollarExchangeRate(dollarPriceToBrl: Double?) {}
    override fun saveBitcoinPrices(btc: CoinPrice?) {}
    override fun saveEtherumPrices(eth: CoinPrice?) {}
}