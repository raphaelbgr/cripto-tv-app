package com.raphaelbgr.tvapp.mycryptopricetvapp.home

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.raphaelbgr.tvapp.mycryptopricetvapp.R
import com.raphaelbgr.tvapp.mycryptopricetvapp.presenter.GridItemContract
import com.raphaelbgr.tvapp.mycryptopricetvapp.presenter.IconHeaderPresenter

class MainFragment : BrowseSupportFragment() {

    private lateinit var mCategoryRowAdapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareUi()
        prepareRows()
        prepareEntranceTransition()
        startEntranceTransition()
    }

    private fun prepareUi() {
        title = getString(R.string.app_name)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.search_opaque)
        mCategoryRowAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mCategoryRowAdapter

        setHeaderPresenterSelector(object : PresenterSelector() {
            override fun getPresenter(o: Any): Presenter {
                return IconHeaderPresenter()
            }
        })
    }

    private fun prepareRows() {
        val gridHeader = HeaderItem(getString(R.string.bitcoin))
        val gridPresenter = GridItemContract(viewLifecycleOwner)
        val gridRowAdapter = ArrayObjectAdapter(gridPresenter)
        gridRowAdapter.add(getString(R.string.price_usd))
        gridRowAdapter.add(getString(R.string.price_brl))
        val row = ListRow(gridHeader, gridRowAdapter)
        mCategoryRowAdapter.add(row)
    }
}