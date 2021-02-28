package com.raphaelbgr.tvapp.mycryptopricetvapp

import com.raphaelbgr.tvapp.mycryptopricetvapp.data.TestUrlProviderImpl

class TvTestApplication : TvApplication() {

    override fun onCreate() {
        super.onCreate()
        urlProvider = TestUrlProviderImpl()
    }
}