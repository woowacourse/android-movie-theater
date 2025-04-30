package woowacourse.movie.presentation.view.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.view.MovieTheaterActivity
import woowacourse.movie.presentation.view.movies.adapter.OnMovieEventListener
import woowacourse.movie.presentation.view.movies.dialog.TheaterBottomSheetDialogFragment
import woowacourse.movie.presentation.view.reservation.detail.ReservationDetailFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View {
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this) }
    private val views: MoviesViews by lazy { MoviesViews(requireContext(), binding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as? MovieTheaterActivity)?.setVisibleBottomNavigation(true)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        showActionBarBackButton(false)

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
        val fragment = ReservationDetailFragment.newInstance(movie, theater)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET_DIALOG_TAG = "theater select dialog"
    }
}
