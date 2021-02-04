package com.raphaelbgr.tvapp.mycryptopricetvapp.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class BitcoinPrices(
    @SerializedName("BRL")
    val bRL: BRL?,
    @SerializedName("USD")
    val uSD: USD?,
    var lastUpdated: Date?
) {
    override fun toString(): String {
        return "BitcoinPrices(bRL=$bRL, uSD=$uSD, lastUpdated=$lastUpdated)"
    }
}