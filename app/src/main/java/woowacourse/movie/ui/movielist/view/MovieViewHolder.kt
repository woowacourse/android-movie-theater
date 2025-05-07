package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.ui.movielist.view.listener.MovieClickListener
import woowacourse.movie.utils.StringFormatter

class MovieViewHolder(
    private val itemBinding: MovieItemBinding,
    movieClickListener: MovieClickListener,
) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        itemBinding.movieClickListener = movieClickListener
    }

    fun bind(item: Movie) {
        itemBinding.stringFormatter = StringFormatter
        itemBinding.movie = item
    }
}
