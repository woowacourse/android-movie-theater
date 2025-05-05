package woowacourse.movie.feature.theaters.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        screening: ScreeningUiModel,
        onBookingClick: () -> Unit,
    ) {
        binding.screening = screening
        binding.onBookingClick = onBookingClick
    }
}
