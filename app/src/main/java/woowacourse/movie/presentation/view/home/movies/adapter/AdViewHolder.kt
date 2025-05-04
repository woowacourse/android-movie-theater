package woowacourse.movie.presentation.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.view.home.movies.adapter.item.AdItem

class AdViewHolder(
    view: ViewGroup,
) : BaseViewHolder<AdItem, ItemAdBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.item_ad, view, false),
    ) {
    override fun bind(item: AdItem) {
        binding.ivAd.setImageResource(item.resId)
    }
}
