package woowacourse.movie.feature.home.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.home.theater.ui.TheaterUiModel
import woowacourse.movie.model.Theater

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        theaterItemClick: (Int) -> Unit,
    ) {
        binding.theater = TheaterUiModel.of(theater, theaterItemClick, bindingAdapterPosition)
    }
}
