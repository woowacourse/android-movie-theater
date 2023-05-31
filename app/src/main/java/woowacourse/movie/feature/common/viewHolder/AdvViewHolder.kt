package woowacourse.movie.feature.common.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.databinding.ItemAdvBinding
import woowacourse.movie.feature.common.itemModel.AdvItemModel
import woowacourse.movie.feature.common.itemModel.ItemModel

class AdvViewHolder(
    binding: ViewDataBinding
) : ItemViewHolder(binding) {

    override fun bind(itemModel: ItemModel) {
        binding as ItemAdvBinding
        itemModel as AdvItemModel

        binding.advImg.setImageResource(itemModel.advState.imgId)
        binding.advImg.setOnClickListener { itemModel.onClick(itemModel.advState) }
    }
}
