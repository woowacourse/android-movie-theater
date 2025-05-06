package woowacourse.movie.presentation.home.movies.dialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var theater: TheaterUiModel? = null

    init {
        binding.onClickTheater =
            View.OnClickListener {
                theater?.let(onClickTheater)
            }
    }

    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
    }
}
