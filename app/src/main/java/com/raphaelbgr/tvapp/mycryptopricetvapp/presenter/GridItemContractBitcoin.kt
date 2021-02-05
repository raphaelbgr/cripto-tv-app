/*
 * Copyright (c) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.raphaelbgr.tvapp.mycryptopricetvapp.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice
import com.raphaelbgr.tvapp.mycryptopricetvapp.databinding.CryptoTileBinding
import com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel.CryptoPricesViewModel

class GridItemContractBitcoin(private val viewLifecycleOwner: LifecycleOwner) : Presenter() {

    private lateinit var btcObserver: Observer<CoinPrice>

    override fun onCreateViewHolder(parent: ViewGroup): TileViewHolder {
        val binding = CryptoTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TileViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val tileHolder = (viewHolder as TileViewHolder)
        tileHolder.setMode(item as String)
        btcObserver = Observer<CoinPrice> { btc ->
            tileHolder.updateData(btc)
        }

        CryptoPricesViewModel.get().btcObservable.observe(viewLifecycleOwner, btcObserver)

        tileHolder.startRepeatingTask()
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        CryptoPricesViewModel.get().btcObservable.removeObserver(btcObserver)
    }
}