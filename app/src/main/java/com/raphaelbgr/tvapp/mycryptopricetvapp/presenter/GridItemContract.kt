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

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.databinding.CryptoTileBinding
import com.raphaelbgr.tvapp.mycryptopricetvapp.model.BitcoinPrices
import com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel.CryptoPricesViewModel
import org.ocpsoft.prettytime.PrettyTime
import java.text.NumberFormat
import java.util.*

class GridItemContract(private val viewLifecycleOwner: LifecycleOwner) : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): TileViewHolder {
        val binding = CryptoTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TileViewHolder(binding)
    }

    class TileViewHolder(private val binding: CryptoTileBinding) : ViewHolder(binding.root) {
        private var mStatusChecker: Runnable? = null
        private val mHandler = Handler()

        @SuppressLint("SetTextI18n")
        fun updateData(btc: BitcoinPrices?) {
            if (btc != null) {
                binding.tvLastUpdated.text =
                    String.format(
                        "%s %s",
                        binding.tvLastUpdated.context.getText(R.string.last_updated_colon),
                        PrettyTime().format(btc.lastUpdated)
                    )
                val formatUs: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
                val formatBrl: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                formatUs.currency = Currency.getInstance(Locale.US)
                formatBrl.currency = Currency.getInstance(Locale("pt", "BR"))
                val btcUsd: String = formatUs.format(btc.uSD?.buy)
                val btcBrl: String = formatBrl.format(btc.bRL?.buy)
                binding.tvPriceUsd.text = "$btcUsd USD"
                binding.tvPriceBrl.text = "$btcBrl BRL"
                showLoading(false)
            }
        }

        fun showLoading(show: Boolean) {
            if (show)
                binding.progressBar.visibility = VISIBLE
            else
                binding.progressBar.visibility = GONE
        }

        fun startRepeatingTask() {
            mStatusChecker = object : Runnable {
                override fun run() {
                    try {
                        showLoading(true)
                        CryptoPricesViewModel.get().loadData()
                    } finally {
                        mHandler.postDelayed(this, 5000)
                    }
                }
            }
            mStatusChecker?.run()
        }

        fun stopRepeatingTask() {
            mStatusChecker?.let { mHandler.removeCallbacks(it) }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val tileHolder = (viewHolder as TileViewHolder)
        val btcObserver = Observer<BitcoinPrices> { btc ->
            tileHolder.updateData(btc)
        }

        CryptoPricesViewModel.get().btcObservable.observe(viewLifecycleOwner, btcObserver)

        tileHolder.startRepeatingTask()
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
}