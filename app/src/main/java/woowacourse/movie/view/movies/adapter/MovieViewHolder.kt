package woowacourse.movie.view.movies.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.adapter.model.MovieRvItem.MovieItem

class MovieViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val handler: Handler,
) : BaseViewHolder(parent, layoutId) {
    fun bind(item: MovieItem) {
        MovieItemBinding.bind(itemView).apply {
            model = item
            eventListener = handler
        }
    }

    interface Handler {
        fun onClickBooking(movieId: Int)
    }
}
