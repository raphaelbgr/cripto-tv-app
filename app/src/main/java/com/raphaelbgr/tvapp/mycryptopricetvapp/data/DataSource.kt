package com.raphaelbgr.tvapp.mycryptopricetvapp.data

import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice

interface DataSource {

    fun getBitcoinPrices(): CoinPrice?
    fun saveBitcoinPrices(btc: CoinPrice?)
    fun saveEtherumPrices(eth: CoinPrice?)
    fun getEtherumPrices(): CoinPrice?
    fun saveBrlToDollarExchangeRate(dollarPriceToBrl: Double?)
    fun getBrlToDollarExchangeRate(): Double?
}