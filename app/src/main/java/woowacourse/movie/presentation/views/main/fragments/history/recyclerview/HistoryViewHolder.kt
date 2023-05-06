package woowacourse.movie.presentation.views.main.fragments.history.recyclerview

import woowacourse.movie.databinding.ItemHistoryBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.Reservation

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    onClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    override fun <T> bind(item: T) {
        binding.history = (item as? Reservation) ?: return
    }
}
