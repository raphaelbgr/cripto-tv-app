package com.raphaelbgr.tvapp.mycryptopricetvapp.model


import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("buy")
    val buy: Double?,
    @SerializedName("last")
    val last: Double?,
    @SerializedName("15m")
    val m: Double?,
    @SerializedName("sell")
    val sell: Double?,
    @SerializedName("symbol")
    val symbol: String?


) {
    override fun toString(): String {
        return "USD(buy=$buy, last=$last, m=$m, sell=$sell, symbol=$symbol)"
    }
}