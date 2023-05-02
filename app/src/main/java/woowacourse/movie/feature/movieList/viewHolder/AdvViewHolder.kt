package woowacourse.movie.feature.movieList.viewHolder

import woowacourse.movie.databinding.AdvItemLayoutBinding
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.viewHolder.ItemViewHolder
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel

class AdvViewHolder(
    binding: AdvItemLayoutBinding
) : ItemViewHolder(binding) {
    override fun bind(itemModel: ItemModel) {
        val item = itemModel as AdvItemModel
        val binding = binding as AdvItemLayoutBinding
        binding.adv = item
    }
}
