package woowacourse.movie.view.theater

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.domain.model.TheaterUIModel

object TheaterDiffUtil : DiffUtil.ItemCallback<TheaterUIModel>() {
    override fun areItemsTheSame(
        oldItem: TheaterUIModel,
        newItem: TheaterUIModel,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: TheaterUIModel,
        newItem: TheaterUIModel,
    ): Boolean = oldItem == newItem
}
