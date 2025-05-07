package woowacourse.movie.presentation.home.movies

import android.os.Bundle
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.home.movies.adapter.MovieViewHolder
import woowacourse.movie.presentation.home.movies.adapter.MoviesAdapter
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem
import woowacourse.movie.presentation.home.movies.dialog.TheaterBottomSheetDialogFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View,
    MovieViewHolder.OnMovieEventListener {
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this) }
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter(this) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setMoviesAdapter()
        presenter.fetchData()
    }

    override fun showScreen(movies: List<MovieMainItem>) {
        updateMovies(movies)
    }

    override fun showAvailableTheatersAndCount(
        movie: MovieUiModel,
        times: TheatersUiModel,
    ) {
        TheaterBottomSheetDialogFragment
            .newInstance(times, movie)
            .show(parentFragmentManager, THEATER_BOTTOM_SHEET_DIALOG_TAG)
    }

    override fun onMovieClick(movie: MovieUiModel) {
        presenter.availableTheatersAndCount(movie.id)
    }

    private fun setMoviesAdapter() {
        binding.rvMovie.adapter = moviesAdapter
    }

    private fun updateMovies(movies: List<MovieMainItem>) {
        moviesAdapter.submitList(movies)
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET_DIALOG_TAG = "theater select dialog"
    }
}
