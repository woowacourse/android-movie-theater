package woowacourse.movie.home.view.adapter.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
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
