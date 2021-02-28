package com.raphaelbgr.tvapp.mycryptopricetvapp.data

interface UrlProvider {

    fun usdToBrlUrl(): String
    fun btcUrl(): String
    fun ethUrl(): String
}