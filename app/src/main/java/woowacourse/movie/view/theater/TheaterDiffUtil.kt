package woowacourse.movie.view.theater

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.view.model.TheaterUiModel

object TheaterDiffUtil : DiffUtil.ItemCallback<TheaterUiModel>() {
    override fun areItemsTheSame(
        oldItem: TheaterUiModel,
        newItem: TheaterUiModel,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: TheaterUiModel,
        newItem: TheaterUiModel,
    ): Boolean = oldItem == newItem
}
