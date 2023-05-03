package woowacourse.movie.ui.main.viewHolder

import woowacourse.movie.databinding.ItemCinemaBottomSheetBinding
import woowacourse.movie.ui.main.itemModel.BottomSheetItemModel
import woowacourse.movie.ui.main.itemModel.ItemModel

class CinemaListViewHolder(
    private val binding: ItemCinemaBottomSheetBinding,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.bottomSheetArrow.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as? BottomSheetItemModel ?: return
        binding.cinema = item.cinema
    }
}
