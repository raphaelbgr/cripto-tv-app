package com.raphaelbgr.tvapp.mycryptopricetvapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.UrlProvider
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.UrlProviderImpl
import timber.log.Timber

open class TvApplication : Application() {

    companion object {
        var urlProvider: UrlProvider = UrlProviderImpl()

        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        fun getAppContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        super.onTerminate()
        mContext = null
    }

}