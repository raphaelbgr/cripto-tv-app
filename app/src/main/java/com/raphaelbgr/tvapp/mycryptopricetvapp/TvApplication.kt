package com.raphaelbgr.tvapp.mycryptopricetvapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber

class TvApplication : Application() {

    companion object {
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

}