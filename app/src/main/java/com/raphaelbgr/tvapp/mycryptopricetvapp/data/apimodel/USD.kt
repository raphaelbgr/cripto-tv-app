package com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel


import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("buy")
    val buy: Double?
) {
    override fun toString(): String {
        return "USD(buy=$buy)"
    }
}