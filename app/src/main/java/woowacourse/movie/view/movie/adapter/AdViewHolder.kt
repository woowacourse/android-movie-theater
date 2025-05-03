package woowacourse.movie.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.model.AdItem

class AdViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<AdItem, ItemAdvertisementBinding>(
        ItemAdvertisementBinding.inflate(
            LayoutInflater.from(
                parent.context,
            ),
            parent,
            false,
        ),
    ) {
    override fun bind(item: AdItem) {
        super.bind(item)
        binding.ivAdvertisement.setImageResource(item.image)
    }
}
