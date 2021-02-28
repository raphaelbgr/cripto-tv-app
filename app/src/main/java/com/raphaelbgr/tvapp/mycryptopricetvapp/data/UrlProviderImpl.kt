package com.raphaelbgr.tvapp.mycryptopricetvapp.data

class UrlProviderImpl : UrlProvider {

    override fun usdToBrlUrl(): String {
        return "https://blockchain.info/ticker"
    }

    override fun btcUrl(): String {
        return "https://api.blockchain.com/v3/exchange/tickers/BTC-USD"

    }

    override fun ethUrl(): String {
        return "https://api.blockchain.com/v3/exchange/tickers/ETH-USD"
    }
}