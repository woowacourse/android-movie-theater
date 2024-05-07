package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.domain.model.Theater

class TheaterDiffUtil : DiffUtil.ItemCallback<Theater>() {
    override fun areItemsTheSame(
        oldItem: Theater,
        newItem: Theater,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Theater,
        newItem: Theater,
    ): Boolean {
        return oldItem == newItem
    }
}
