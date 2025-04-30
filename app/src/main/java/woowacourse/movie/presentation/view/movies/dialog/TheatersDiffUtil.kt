package woowacourse.movie.presentation.view.movies.dialog

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.model.TheaterUiModel

object TheatersDiffUtil : DiffUtil.ItemCallback<TheaterUiModel>() {
    override fun areItemsTheSame(
        oldItem: TheaterUiModel,
        newItem: TheaterUiModel,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: TheaterUiModel,
        newItem: TheaterUiModel,
    ): Boolean = oldItem == newItem
}
