package com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.DataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.LocalDataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.NetworkDataSource
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoPricesViewModel : ViewModel() {

    val btcObservable: MutableLiveData<CoinPrice> by lazy {
        MutableLiveData<CoinPrice>()
    }

    val ethObservable: MutableLiveData<CoinPrice> by lazy {
        MutableLiveData<CoinPrice>()
    }

    fun loadData() {
        loadBtcData()
        loadEtherumData()
    }

    private fun loadEtherumData() {
        viewModelScope.launch {
            val networkSource: DataSource = NetworkDataSource()
            val localSource: DataSource = LocalDataSource()
            var ethResult = withContext(Dispatchers.Default) {
                networkSource.getEtherumPrices()
            }
            ethResult?.dollarPriceToBrl = localSource.getBrlToDollarExchangeRate() ?: 0.0
            ethResult?.calculateBrlPrice()

            if (ethResult == null) {
                ethResult = withContext(Dispatchers.Default) {
                    localSource.getEtherumPrices()
                }
            }
            if (ethResult != null) {
                localSource.saveEtherumPrices(ethResult)
            }
            this@CryptoPricesViewModel.ethObservable.postValue(ethResult)
        }
    }

    private fun loadBtcData() {
        viewModelScope.launch {
            val networkSource: DataSource = NetworkDataSource()
            val localSource: DataSource = LocalDataSource()
            var btcResult = withContext(Dispatchers.Default) {
                networkSource.getBitcoinPrices()
            }
            localSource.saveBrlToDollarExchangeRate(btcResult?.dollarPriceToBrl)

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

    companion object {
        private lateinit var sInstance: CryptoPricesViewModel

        @MainThread
        fun get(): CryptoPricesViewModel {
            sInstance = if (::sInstance.isInitialized) sInstance else CryptoPricesViewModel()
            return sInstance
        }
    }
}