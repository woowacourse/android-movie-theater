package woowacourse.movie.presentation.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.AdUiModel

class AdViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<AdUiModel, ItemAdvertisementBinding>(
        ItemAdvertisementBinding.inflate(
            LayoutInflater.from(
                parent.context,
            ),
            parent,
            false,
        ),
    ) {
    override fun bind(item: AdUiModel) {
        super.bind(item)
        binding.ivAdvertisement.setImageResource(item.image)
    }
}
