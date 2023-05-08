package woowacourse.movie.presentation.ui.main.fragments.theater.adapter.viewholder

import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.theater.PresentationTheater

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    onClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    override fun <T> bind(item: T) {
        if (item !is PresentationTheater) return
        binding.item = item
    }
}
