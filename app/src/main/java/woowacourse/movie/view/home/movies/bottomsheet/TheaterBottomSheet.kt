package woowacourse.movie.view.home.movies.bottomsheet

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.ext.showToast
import woowacourse.movie.view.home.booking.BookingActivity
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.movies.model.ScreeningInfo

class TheaterBottomSheet :
    BottomSheetDialogFragment(R.layout.fragment_theater_bottom_sheet),
    TheaterListContract.View,
    TheaterListEventHandler {
    private val presenter: TheaterListContract.Presenter by lazy {
        TheaterListPresenter(this, TheaterStore())
    }
    private var _binding: FragmentTheaterBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val movieId: Int by lazy { initMovieId() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTheaterBottomSheetBinding.bind(view)
        presenter.loadTheaters(movieId)
    }

    private fun initMovieId(): Int {
        return arguments?.getInt(KEY_MOVIE_ID) ?: run {
            activity?.showToast(getString(R.string.text_error))
            parentFragmentManager.beginTransaction().remove(this).commit()
            MOVIE_ID_NOT_INITIALIZED
        }
    }

    override fun showTheaters(theaters: Theaters) {
        binding.rv.adapter = TheaterAdapter(movieId, theaters, this)
    }

    override fun moveToBooking(screeningInfo: ScreeningInfo) {
        val intent = BookingActivity.newIntent(requireContext(), screeningInfo)
        startActivity(intent)
    }

    override fun onTheaterSelected(theater: Theater) {
        presenter.selectTheater(movieId, theater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MOVIE_ID_NOT_INITIALIZED = -1
        private const val KEY_MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Int): TheaterBottomSheet {
            val arguments = Bundle().apply { putInt(KEY_MOVIE_ID, movieId) }
            return TheaterBottomSheet().apply { this.arguments = arguments }
        }
    }
}
