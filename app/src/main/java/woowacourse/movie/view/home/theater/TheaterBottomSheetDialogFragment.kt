package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.Showing
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.getMovieById
import woowacourse.movie.view.reservation.detail.ReservationActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this)
    }
    private var _binding: FragmentTheaterBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater_bottom_sheet_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(KEY_MOVIE_ID, 0)

        if (movieId == null || movieId == 0) {
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
        showings: List<Showing>,
        movieUi: MovieUi,
    ) {
        binding.showings = showings

        binding.onItemClick =
            OnTheaterEventListener { showing ->
                navigateToReservation(movieUi, showing)
                dismiss()
            }
    }

    private fun navigateToReservation(
        movieUi: MovieUi,
        showings: Showing,
    ) {
        val intent = ReservationActivity.newIntent(requireContext(), movieUi.movieId, showings)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Int): TheaterBottomSheetDialogFragment {
            return TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(KEY_MOVIE_ID, movieId)
                    }
            }
        }
    }
}
