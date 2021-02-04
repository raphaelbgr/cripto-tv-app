package com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.DataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.LocalDataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.NetworkDataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.model.BitcoinPrices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoPricesViewModel : ViewModel() {

    val btcObservable: MutableLiveData<BitcoinPrices> by lazy {
        MutableLiveData<BitcoinPrices>()
    }

    fun loadData() {
        viewModelScope.launch {
            val networkSource: DataSource = NetworkDataSource()
            val localSource: DataSource = LocalDataSource()
            var btcResult = withContext(Dispatchers.Default) {
                networkSource.getBitcoinPrices()
            }
            if (btcResult == null) {
                btcResult = withContext(Dispatchers.Default) {
                    localSource.getBitcoinPrices()
                }
            }
            if (btcResult != null) {
                localSource.saveBitcoinPrices(btcResult)
            }
            this@CryptoPricesViewModel.btcObservable.postValue(btcResult)
        }
    }

    fun loadCachedData() {
        viewModelScope.launch {
            val localSource: DataSource = LocalDataSource()
            val btcResult = withContext(Dispatchers.Default) {
                localSource.getBitcoinPrices()
            }
            this@CryptoPricesViewModel.btcObservable.postValue(btcResult)
        }
    }

    companion object {
        private lateinit var sInstance: CryptoPricesViewModel

        @MainThread
        fun get(): CryptoPricesViewModel {
            sInstance = if (::sInstance.isInitialized) sInstance else CryptoPricesViewModel()
            return sInstance
        }
    }
}