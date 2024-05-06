package woowacourse.movie.feature.home.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.home.theater.ui.TheaterUiModel
import woowacourse.movie.model.Theater

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        onTheaterItemClick: TheaterItemClickListener,
    ) {
        binding.theater = TheaterUiModel.of(theater)
        binding.root.setOnClickListener {
            onTheaterItemClick(bindingAdapterPosition)
        }
    }
}
