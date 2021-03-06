package com.raphaelbgr.tvapp.mycryptopricetvapp.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.raphaelbgr.tvapp.mycryptopricetvapp.R

class IconHeaderPresenter : RowHeaderPresenter() {
    private var mUnselectedAlpha = 0f
    override fun onCreateViewHolder(viewGroup: ViewGroup): ViewHolder {
        mUnselectedAlpha = viewGroup.resources
            .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1)
        val inflater = viewGroup.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.icon_header_item, null)
        view.alpha = mUnselectedAlpha // Initialize icons to be at half-opacity.
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val headerItem = (item as ListRow).headerItem
        val rootView = viewHolder.view
        rootView.isFocusable = true
        val iconView = rootView.findViewById<View>(R.id.header_icon) as ImageView
        val iconRes =
            if (headerItem.name == "Etherum") R.drawable.ic_ethereum_logo_wine else R.drawable.ic_bitcoin
        val icon =
            ResourcesCompat.getDrawable(rootView.context.resources, iconRes, null)
        iconView.setImageDrawable(icon)
        val label = rootView.findViewById<View>(R.id.header_label) as TextView
        label.text = headerItem.name
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}

    override fun onSelectLevelChanged(holder: ViewHolder) {
        holder.view.alpha = mUnselectedAlpha + holder.selectLevel *
                (1.0f - mUnselectedAlpha)
    }
}