package woowacourse.movie.presentation.view.home.movies

import android.os.Bundle
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.view.home.movies.adapter.MoviesAdapter
import woowacourse.movie.presentation.view.home.movies.adapter.OnMovieEventListener
import woowacourse.movie.presentation.view.home.movies.dialog.TheaterBottomSheetDialogFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View {
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val moviesAdapter =
            MoviesAdapter(
                object : OnMovieEventListener {
                    override fun onClick(movie: MovieUiModel) {
                        presenter.availableTheatersAndCount(movie.id)
                    }
                },
            )
        binding.rvMovie.adapter = moviesAdapter

        presenter.fetchData()
    }

    override fun showScreen(movies: List<MovieUiModel>) {
        binding.movieList = movies
    }

    override fun showAvailableTheatersAndCount(
        movie: MovieUiModel,
        times: TheatersUiModel,
    ) {
        TheaterBottomSheetDialogFragment
            .newInstance(times, movie)
            .show(parentFragmentManager, THEATER_BOTTOM_SHEET_DIALOG_TAG)
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET_DIALOG_TAG = "theater select dialog"
    }
}
