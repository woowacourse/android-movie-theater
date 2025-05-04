package woowacourse.movie.presentation.home.movies.dialog

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.common.model.TheaterUiModel

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
