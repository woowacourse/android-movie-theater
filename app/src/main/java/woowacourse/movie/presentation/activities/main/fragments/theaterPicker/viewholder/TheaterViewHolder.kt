package woowacourse.movie.presentation.activities.main.fragments.theaterPicker.viewholder

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.Theater

class TheaterViewHolder(
    parent: ViewGroup,
    onClick: (Int) -> Unit,
) : BaseViewHolder(parent, R.layout.item_theater) {
    private val binding = ItemTheaterBinding.bind(itemView)

    init {
        binding.next.setOnClickListener { onClick(adapterPosition) }
    }

    fun bind(theater: Theater) {
        binding.theater = theater
    }
}
