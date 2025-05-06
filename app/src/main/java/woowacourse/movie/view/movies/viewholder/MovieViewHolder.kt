package woowacourse.movie.view.movies.viewholder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.model.MovieRvItem.MovieItem

class MovieViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val handler: Handler,
) : BaseViewHolder<MovieItem>(parent, layoutId) {
    override fun bind(item: MovieItem) {
        MovieItemBinding.bind(itemView).apply {
            model = item
            eventListener = handler
        }
    }

    interface Handler {
        fun onClickBooking(movieId: Int)
    }
}
