package woowacourse.movie.view.home.movies.viewholder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.home.movies.model.MovieRvItem

class MovieViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val handler: Handler,
) : BaseViewHolder<MovieRvItem.MovieItem>(parent, layoutId) {
    override fun bind(movieRvItem: MovieRvItem.MovieItem) {
        MovieItemBinding.bind(itemView).apply {
            model = movieRvItem
            eventListener = handler
        }
    }

    interface Handler {
        fun onClickBooking(movieId: Int)
    }
}
