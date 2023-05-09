package woowacourse.movie.feature.movieList.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.databinding.ItemaAdvLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel

class AdvViewHolder(
    binding: ViewDataBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        itemModel as AdvItemModel
        binding as ItemaAdvLayoutBinding
        binding.adv = itemModel
    }
}
