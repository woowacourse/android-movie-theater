package woowacourse.movie.presentation.view.movies

import android.os.Bundle
import android.view.View
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.view.ReservationActivity
import woowacourse.movie.presentation.view.movies.adapter.OnMovieEventListener
import woowacourse.movie.presentation.view.movies.dialog.TheaterBottomSheetDialogFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View {
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this) }
    private val views: MoviesViews by lazy { MoviesViews(requireContext(), binding) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        views.bind(
            object : OnMovieEventListener {
                override fun onClick(movie: MovieUiModel) {
                    presenter.availableTheatersAndCount(movie.id)
                }
            },
        )
        presenter.fetchData()
    }

    override fun showScreen(movies: List<MovieUiModel>) {
        views.updateMovies(movies)
    }

    override fun showAvailableTheatersAndCount(
        movie: MovieUiModel,
        times: TheatersUiModel,
    ) {
        TheaterBottomSheetDialogFragment
            .newInstance(times) { theaters ->
                navigateToReservationScreen(movie, theaters)
            }.show(parentFragmentManager, THEATER_BOTTOM_SHEET_DIALOG_TAG)
    }

    private fun navigateToReservationScreen(
        movie: MovieUiModel,
        theater: TheaterUiModel,
    ) {
        val intent = ReservationActivity.newIntent(requireContext(), movie, theater)
        startActivity(intent)
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET_DIALOG_TAG = "theater select dialog"
    }
}
