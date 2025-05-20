package woowacourse.movie.view.home.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.OnMovieEventListener

class MovieViewHolder(
    eventListener: OnMovieEventListener,
    val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onMovieEventListener = eventListener
    }

    fun bind(movieUi: MovieUi) {
        binding.movieUi = movieUi
    }
}
