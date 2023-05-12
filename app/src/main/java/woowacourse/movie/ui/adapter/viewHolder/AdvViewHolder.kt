package woowacourse.movie.ui.adapter.viewHolder

import woowacourse.movie.databinding.ItemAdvLayoutBinding
import woowacourse.movie.ui.adapter.itemModel.AdvItemModel
import woowacourse.movie.ui.adapter.itemModel.ItemModel

class AdvViewHolder(
    private val binding: ItemAdvLayoutBinding,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.advImg.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as AdvItemModel
        binding.adv = item.advState
    }
}
