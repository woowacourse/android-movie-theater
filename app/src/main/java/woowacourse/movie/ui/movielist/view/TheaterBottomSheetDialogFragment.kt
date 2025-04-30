package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.sample.DUMMY_THEATERS
import woowacourse.movie.ui.booking.view.BookingActivity
import woowacourse.movie.utils.bundleSerializable

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
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

        val adapter =
            TheaterAdapter { theater ->
                startBookingActivity(view, theater)
            }

        val movie =
            arguments?.bundleSerializable("EXTRA_MOVIE", Movie::class.java) ?: DUMMY_MOVIES.first()

        binding.theatersRecyclerView.adapter = adapter
        val theaters = DUMMY_THEATERS.availableTheaters(movie)
        adapter.submitList(theaters.theaters.toList())
    }

    private fun startBookingActivity(
        view: View,
        theater: Theater,
    ) {
        if (theater.movieSchedules.isNotEmpty()) {
            startActivity(BookingActivity.newIntent(view.context, theater))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable("EXTRA_MOVIE", movie)
                    }
            }
    }
}
