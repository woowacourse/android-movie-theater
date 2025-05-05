package woowacourse.movie.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.Movie

class MovieViewHolder(
    private val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindMovie(movie: Movie, movieClickListener: MovieClickListener) {
        binding.movie = movie
        binding.movieClickListener = movieClickListener
    }
}
