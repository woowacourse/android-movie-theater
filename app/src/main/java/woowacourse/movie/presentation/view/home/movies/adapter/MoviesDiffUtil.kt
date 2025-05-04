package woowacourse.movie.presentation.view.home.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.view.home.movies.adapter.item.MovieMainItem

object MoviesDiffUtil : DiffUtil.ItemCallback<MovieMainItem>() {
    override fun areItemsTheSame(
        oldItem: MovieMainItem,
        newItem: MovieMainItem,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieMainItem,
        newItem: MovieMainItem,
    ): Boolean = oldItem == newItem
}
