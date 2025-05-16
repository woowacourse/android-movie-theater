package woowacourse.movie.presentation.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem

class AdViewHolder(
    view: ViewGroup,
) : BaseViewHolder<MovieMainItem.AdItem, ItemAdBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.item_ad, view, false),
    ) {
    override fun bind(item: MovieMainItem.AdItem) {
        binding.advertisement = item.resId
    }
}
