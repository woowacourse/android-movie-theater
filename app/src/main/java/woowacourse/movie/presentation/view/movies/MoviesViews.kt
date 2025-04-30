package woowacourse.movie.presentation.view.movies

import android.content.Context
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.view.movies.adapter.MoviesAdapter
import woowacourse.movie.presentation.view.movies.adapter.OnMovieEventListener

class MoviesViews(
    private val context: Context,
    private val binding: FragmentMoviesBinding,
) {
    private lateinit var moviesAdapter: MoviesAdapter

    fun bind(listener: OnMovieEventListener) {
        moviesAdapter = MoviesAdapter(listener)
        binding.rvMovie.adapter = moviesAdapter
    }

    fun updateMovies(movies: List<MovieUiModel>) {
        moviesAdapter.submitList(movies)
    }
}
