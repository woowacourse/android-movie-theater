package woowacourse.movie.presentation.home.movies.dialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
        binding.onClickTheater = View.OnClickListener { onClickTheater(theater) }
    }
}
