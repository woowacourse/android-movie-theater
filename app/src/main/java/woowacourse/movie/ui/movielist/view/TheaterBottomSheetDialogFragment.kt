package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.ui.booking.view.BookingActivity
import woowacourse.movie.ui.movielist.contract.TheaterBottomSheetDialogContract
import woowacourse.movie.ui.movielist.presenter.TheaterBottomSheetDialogPresenter

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterBottomSheetDialogContract.View {
    private var _binding: FragmentTheaterBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val presenter by lazy { TheaterBottomSheetDialogPresenter(this) }
    private val theaterAdapter: TheaterAdapter by lazy { generateAdapter(restoreMovieId()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_theater_bottom_sheet_dialog,
                container,
                false,
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = restoreMovieId()

        binding.theatersRecyclerView.adapter = theaterAdapter
        presenter.loadAvailableTheaters(movieId)
    }

    override fun showTheaters(theaters: Theaters) {
        theaterAdapter.submitList(theaters.theaters)
    }

    override fun showReservation(
        theater: Theater,
        movieId: Long,
    ) {
        if (theater.theaterSchedules[movieId].isNotEmpty()) {
            startActivity(BookingActivity.newIntent(binding.root.context, theater, movieId))
            parentFragmentManager.commit {
                remove(this@TheaterBottomSheetDialogFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun restoreMovieId(): Long {
        val movieId = arguments?.getLong("EXTRA_MOVIE_ID") ?: 0L
        return movieId
    }

    private fun generateAdapter(movieId: Long): TheaterAdapter {
        return TheaterAdapter(movieId) { theater ->
            presenter.startBooking(theater)
        }
    }

    companion object {
        const val THEATER_DIALOG_TAG = "THEATER_BOTTOM_DIALOG"

        @JvmStatic
        fun newInstance(movieId: Long) =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putLong("EXTRA_MOVIE_ID", movieId)
                    }
            }
    }
}
