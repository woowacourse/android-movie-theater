package woowacourse.movie.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding

class MovieViewHolder(
    private val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindMovie(
        movie: FeedItem.MovieItem,
        movieClickListener: MovieClickListener,
    ) {
        binding.movie = movie.movie
        binding.movieClickListener = movieClickListener
    }
}
