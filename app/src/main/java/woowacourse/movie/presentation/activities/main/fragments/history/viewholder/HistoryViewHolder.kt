package woowacourse.movie.presentation.activities.main.fragments.history.viewholder

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemHistoryBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.Reservation

class HistoryViewHolder(
    parent: ViewGroup,
    onClick: (Int) -> Unit,
) : BaseViewHolder(parent, R.layout.item_history) {
    private val binding = ItemHistoryBinding.bind(itemView)

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
        binding.movieTitleTextView.setOnClickListener { onClick(adapterPosition) }
    }

    fun bind(reservation: Reservation) {
        binding.reservation = reservation
    }
}
