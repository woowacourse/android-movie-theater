package woowacourse.movie.presentation.home.movies.dialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    eventListener: OnTheaterEventListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onTheaterClick = eventListener
    }

    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
    }

    interface OnTheaterEventListener {
        fun onTheaterClick(theater: TheaterUiModel)
    }
}
