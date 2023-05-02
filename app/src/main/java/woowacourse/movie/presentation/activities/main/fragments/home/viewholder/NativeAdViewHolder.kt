package woowacourse.movie.presentation.activities.main.fragments.home.viewholder

import android.view.View
import android.widget.ImageView
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation

class NativeAdViewHolder(
    view: View,
    onAdClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(view) {
    private val nativeAdsImageView: ImageView = view.findViewById(R.id.native_ad_iv)

    init {
        nativeAdsImageView.setOnClickListener { onAdClick(adapterPosition) }
    }

    override fun bind(item: ListItem) {
        when (item) {
            is Ad -> {
                nativeAdsImageView.setImageResource(item.bannerResId)
            }
            is Movie -> {}
            is Reservation -> {}
        }
    }
}
