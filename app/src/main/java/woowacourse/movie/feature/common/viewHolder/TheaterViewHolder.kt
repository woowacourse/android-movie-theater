package woowacourse.movie.feature.common.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.databinding.ItemTheaterLayoutBinding
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.itemModel.TheaterItemModel

class TheaterViewHolder(binding: ViewDataBinding) : ItemViewHolder(binding) {
    override fun bind(itemModel: ItemModel) {
        binding as ItemTheaterLayoutBinding
        itemModel as TheaterItemModel
        binding.itemModel = itemModel
    }
}
