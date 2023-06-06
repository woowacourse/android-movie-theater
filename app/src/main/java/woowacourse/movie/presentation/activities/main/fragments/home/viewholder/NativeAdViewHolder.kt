package woowacourse.movie.presentation.activities.main.fragments.home.viewholder

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemNativeAdBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem

class NativeAdViewHolder(
    parent: ViewGroup,
    onAdClick: (Int) -> Unit,
) : BaseViewHolder(parent, R.layout.item_native_ad) {
    private val binding = ItemNativeAdBinding.bind(itemView)

    init {
        binding.nativeAdIv.setOnClickListener { onAdClick(adapterPosition) }
    }

    override fun bind(item: ListItem) {
        binding.ad = item as Ad
    }
}
