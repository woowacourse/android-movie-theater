package woowacourse.movie.presentation.view.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    clickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.theaterClickListener = clickListener
    }

    fun bind(theaterUIModel: TheaterUiModel) {
        binding.theater = theaterUIModel
    }
}
