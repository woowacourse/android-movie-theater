package woowacourse.movie.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(
    parent: ViewGroup,
) : ContentViewHolder<ContentItem.Advertisement, ItemAdvertisementBinding>(
        ItemAdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    override fun bind(item: ContentItem.Advertisement) {
        super.bind(item)
        binding.advertisement = item.value
    }
}
