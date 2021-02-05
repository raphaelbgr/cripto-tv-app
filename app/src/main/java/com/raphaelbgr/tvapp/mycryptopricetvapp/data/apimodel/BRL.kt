package com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel


import com.google.gson.annotations.SerializedName

data class BRL(
    @SerializedName("buy")
    var buy: Double?
) {
    override fun toString(): String {
        return "BRL(buy=$buy)"
    }
}