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
        private var mode: String = "price_usd"
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
                val format: NumberFormat
                var btcValue: String
                when (mode) {
                    "USD" -> {
                        binding.tvTileTitle.text = view.context.getString(R.string.american_dollar)
                        format = NumberFormat.getCurrencyInstance(Locale.US)
                        format.currency = Currency.getInstance(Locale.US)
                        btcValue = format.format(btc.uSD?.buy)
                    }
                    else -> {
                        binding.tvTileTitle.text = view.context.getString(R.string.brazillian_real)
                        format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                        format.currency = Currency.getInstance(Locale("pt", "BR"))
                        btcValue = format.format(btc.bRL?.buy)
                    }
                }

                binding.tvPrice.text = btcValue
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

        fun setMode(mode: String) {
            this.mode = mode
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val tileHolder = (viewHolder as TileViewHolder)
        tileHolder.setMode(item as String)
        val btcObserver = Observer<BitcoinPrices> { btc ->
            tileHolder.updateData(btc)
        }

        CryptoPricesViewModel.get().btcObservable.observe(viewLifecycleOwner, btcObserver)

        tileHolder.startRepeatingTask()
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
}