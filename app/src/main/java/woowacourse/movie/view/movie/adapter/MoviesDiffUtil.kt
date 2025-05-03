package woowacourse.movie.view.movie.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.view.item.Movie

object MoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean = oldItem == newItem
}
