package woowacourse.movie.presentation.views.main.fragments.home.recyclerview

import android.view.View
import android.widget.ImageView
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.Ad

class NativeAdViewHolder(
    view: View,
    onAdClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(view) {
    private val nativeAdsImageView: ImageView = view.findViewById(R.id.native_ad_iv)

    init {
        nativeAdsImageView.setOnClickListener { onAdClick(adapterPosition) }
    }

    override fun <T> bind(item: T) {
        if (item !is Ad) return
        nativeAdsImageView.setImageResource(item.bannerResId)
    }
}
