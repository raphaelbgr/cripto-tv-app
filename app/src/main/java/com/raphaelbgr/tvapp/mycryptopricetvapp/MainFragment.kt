package com.raphaelbgr.tvapp.mycryptopricetvapp

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.raphaelbgr.tvapp.mycryptopricetvapp.presenter.GridItemPresenter
import com.raphaelbgr.tvapp.mycryptopricetvapp.presenter.IconHeaderPresenter

class MainFragment : BrowseSupportFragment() {

    private lateinit var mCategoryRowAdapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        title = getString(R.string.app_name)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.search_opaque)
        prepareEntranceTransition()

        setHeaderPresenterSelector(object : PresenterSelector() {
            override fun getPresenter(o: Any): Presenter {
                return IconHeaderPresenter()
            }
        })

        mCategoryRowAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mCategoryRowAdapter


        // Create a row for this special case with more samples.
        val gridHeader = HeaderItem(getString(R.string.bitcoin))
        val gridPresenter = GridItemPresenter()
        val gridRowAdapter = ArrayObjectAdapter(gridPresenter)
        gridRowAdapter.add(getString(R.string.bitcoin))
        val row = ListRow(gridHeader, gridRowAdapter)
        mCategoryRowAdapter.add(row)

        startEntranceTransition()
    }
}