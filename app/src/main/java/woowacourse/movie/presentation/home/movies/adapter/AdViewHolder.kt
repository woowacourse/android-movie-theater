package woowacourse.movie.presentation.home.movies.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.home.movies.adapter.item.AdItem

class AdViewHolder(
    view: ViewGroup,
) : BaseViewHolder<AdItem, ItemAdBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.item_ad, view, false),
    ) {
    override fun bind(item: AdItem) {
        val bitmap = BitmapFactory.decodeResource(itemView.resources, item.resId)
        binding.advertisement = bitmap
    }
}
