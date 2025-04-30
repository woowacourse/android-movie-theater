package woowacourse.movie.view.theater

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.model.Theater

object TheaterDiffUtil : DiffUtil.ItemCallback<Theater>() {
    override fun areItemsTheSame(
        oldItem: Theater,
        newItem: Theater,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Theater,
        newItem: Theater,
    ): Boolean = oldItem == newItem
}
