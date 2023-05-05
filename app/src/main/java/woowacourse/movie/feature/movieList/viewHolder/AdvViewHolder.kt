package woowacourse.movie.feature.movieList.viewHolder

import woowacourse.movie.databinding.AdvItemLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel

class AdvViewHolder(
    binding: AdvItemLayoutBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        val item = itemModel as AdvItemModel
        val binding = binding as AdvItemLayoutBinding
        binding.adv = item
    }
}
