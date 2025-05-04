package woowacourse.movie.presentation.home.movies

import android.os.Bundle
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.common.base.BaseFragment
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.home.movies.adapter.MoviesAdapter
import woowacourse.movie.presentation.home.movies.dialog.TheaterBottomSheetDialogFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View {
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this) }
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter(MovieEventListener(presenter)) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setMoviesAdapter()
        presenter.fetchData()
    }

    override fun showScreen(movies: List<MovieUiModel>) {
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

    private fun setMoviesAdapter() {
        binding.rvMovie.adapter = moviesAdapter
    }

    private fun updateMovies(movies: List<MovieUiModel>) {
        binding.movieList = movies
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET_DIALOG_TAG = "theater select dialog"
    }
}
