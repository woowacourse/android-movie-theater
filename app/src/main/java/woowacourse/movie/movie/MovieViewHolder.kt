package woowacourse.movie.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding

class MovieViewHolder(
    private val binding: MovieItemBinding,
    movieClickListener: MovieClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.movieClickListener = movieClickListener
    }

    fun bindMovie(
        movie: FeedItem.MovieItem,

    ) {
        binding.movie = movie.movie

    }
}
