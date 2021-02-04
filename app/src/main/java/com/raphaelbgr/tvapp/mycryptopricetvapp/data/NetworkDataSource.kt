package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import android.util.Log
import com.google.gson.Gson
import com.raphaelbgr.tvapp.mycryptopricetvapp.model.BitcoinPrices
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class NetworkDataSource : DataSource {

    private val tag = NetworkDataSource::class.java.simpleName

    override fun getBitcoinPrices(): BitcoinPrices? {
        var reader: BufferedReader? = null
        val url = URL("https://blockchain.info/ticker")
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
            val json = sb.toString()
            val data = Gson().fromJson(json, BitcoinPrices::class.java)
            data.lastUpdated = Date()
            Timber.d(data.toString())
            return data
        } catch (e: Exception) {
            return null
        } finally {
            urlConnection.disconnect()
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e(
                        tag,
                        "JSON feed closed",
                        e
                    )
                }
            }
        }
    }

    override fun saveBitcoinPrices(btc: BitcoinPrices?) {

    }
}