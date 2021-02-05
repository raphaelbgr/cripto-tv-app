package com.raphaelbgr.tvapp.mycryptopricetvapp.presenter

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import androidx.leanback.widget.Presenter
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.data.apimodel.CoinPrice
import com.raphaelbgr.tvapp.mycryptopricetvapp.databinding.CryptoTileBinding
import com.raphaelbgr.tvapp.mycryptopricetvapp.viewmodel.CryptoPricesViewModel
import org.ocpsoft.prettytime.PrettyTime
import java.text.NumberFormat
import java.util.*

class TileViewHolder(private val binding: CryptoTileBinding) : Presenter.ViewHolder(binding.root) {
    private var mode: String = "price_usd"
    private var mStatusChecker: Runnable? = null
    private val mHandler = Handler()

    @SuppressLint("SetTextI18n")
    fun updateData(btc: CoinPrice?) {
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
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
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