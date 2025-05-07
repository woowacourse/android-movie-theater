package woowacourse.movie.feature.theaters.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(screening: ScreeningUiModel) {
        binding.screening = screening
        binding.handler = handler
    }

    interface Handler {
        fun onBookingClick(screening: ScreeningUiModel)
    }
}
