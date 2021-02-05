package com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel


import com.google.gson.annotations.SerializedName
import java.util.*

data class CoinPrice(
    @SerializedName("BRL")
    val bRL: BRL?,
    @SerializedName("USD")
    val uSD: USD?,
    var lastUpdated: Date?,
    var dollarPriceToBrl: Double
) {
    override fun toString(): String {
        return "BitcoinPrices(bRL=$bRL, uSD=$uSD, lastUpdated=$lastUpdated, dollarPriceToBrl=$dollarPriceToBrl)"
    }

    fun calculateBrlPriceToDollar() {
        dollarPriceToBrl = bRL?.buy!! / uSD?.buy!!
    }

    fun calculateBrlPrice() {
        bRL?.buy = bRL?.buy!!.times(dollarPriceToBrl)
    }
}