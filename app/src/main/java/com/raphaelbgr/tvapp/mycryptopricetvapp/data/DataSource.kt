package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import com.raphaelbgr.tvapp.mycryptopricetvapp.model.BitcoinPrices

interface DataSource {

    fun getBitcoinPrices(): BitcoinPrices?
    fun saveBitcoinPrices(btc: BitcoinPrices?)
}