package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.MovieId
import woowacourse.movie.domain.Showings
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.movies.getMovieById
import woowacourse.movie.view.reservation.detail.ReservationActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getSerializable(KEY_MOVIE_ID) as? MovieId?

        if (movieId == null) {
            handleInvalidTicket()
        } else {
            val movie = getMovieById(movieId)
            presenter.fetchData(movie)
        }
    }

    override fun handleInvalidTicket() {
        DialogFactory().showError(requireContext()) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showTheaterList(
        showings: List<Showings>,
        movieUi: MovieUi,
    ) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_theater_category)

        val theaterAdapter: TheaterAdapter =
            TheaterAdapter { showing ->
                navigateToReservation(movieUi, showing)
                dismiss()
            }

        recyclerView?.adapter = theaterAdapter
        theaterAdapter.submitList(showings)
    }

    private fun navigateToReservation(
        movieUi: MovieUi,
        showings: Showings,
    ) {
        val intent = ReservationActivity.newIntent(requireContext(), movieUi.movieId, showings)
        startActivity(intent)
    }

    companion object {
        private const val KEY_MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: MovieId): TheaterBottomSheetDialogFragment {
            return TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(KEY_MOVIE_ID, movieId)
                    }
            }
        }
    }
}
