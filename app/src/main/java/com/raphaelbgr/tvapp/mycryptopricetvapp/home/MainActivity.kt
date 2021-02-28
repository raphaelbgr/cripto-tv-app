package com.raphaelbgr.tvapp.mycryptopricetvapp.home

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel.CryptoPricesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class MainActivity : FragmentActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserverData()
        CryptoPricesViewModel.get().loadExchangeRate()
    }

    private fun setupObserverData() {
        val observer = Observer<Double> { rate ->
            run {
                Timber.d(rate.toString())
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        Thread.sleep(10000)
                        CryptoPricesViewModel.get().loadExchangeRate()
                    }
                }
            }
        }
        CryptoPricesViewModel.get().exchangeRateObservable.observe(
            this, observer
        )
    }
}