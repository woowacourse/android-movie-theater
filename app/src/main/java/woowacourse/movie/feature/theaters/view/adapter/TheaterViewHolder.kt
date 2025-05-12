package woowacourse.movie.feature.theaters.view.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    handler: Handler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(screening: ScreeningUiModel) {
        binding.screening = screening
        binding.executePendingBindings()
    }

    interface Handler {
        fun onBookingClick(screening: ScreeningUiModel)
    }
}
