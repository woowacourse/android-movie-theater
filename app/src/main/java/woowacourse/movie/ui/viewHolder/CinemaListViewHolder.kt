package woowacourse.movie.ui.viewHolder

import woowacourse.movie.databinding.ItemCinemaBottomSheetBinding
import woowacourse.movie.ui.itemModel.CinemaItemModel
import woowacourse.movie.ui.itemModel.ItemModel

class CinemaListViewHolder(
    private val binding: ItemCinemaBottomSheetBinding,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.bottomSheetArrow.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as? CinemaItemModel ?: return
        binding.cinemaName = item.cinema.name
        binding.screeningTimes = item.movie.screeningTimes.size
    }
}
