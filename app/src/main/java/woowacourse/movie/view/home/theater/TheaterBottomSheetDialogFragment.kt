package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetFragmentTheaterBinding
import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.reservation.ReservationActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFragmentTheaterBinding
    private lateinit var theaterAdapter: TheaterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.bottom_sheet_fragment_theater,
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

        if (::theaterAdapter.isInitialized.not()) {
            theaterAdapter =
                TheaterAdapter(
                    arguments?.getSerializableExtraData<TheaterMovieSchedules>(THEATER_KEY)?.value
                        ?: emptyList(),
                    ::navigateToReservation,
                )
        }

        binding.theaters.adapter = theaterAdapter
    }

    private fun navigateToReservation(theaterMovieSchedule: TheaterMovieSchedule) {
        val intent = ReservationActivity.getIntent(requireContext(), theaterMovieSchedule)
        startActivity(intent)
        dismiss()
    }

    companion object {
        private const val THEATER_KEY = "theater"

        @JvmStatic
        fun newInstance(theaterMovieSchedules: TheaterMovieSchedules): TheaterBottomSheetDialogFragment =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(THEATER_KEY, theaterMovieSchedules)
                    }
            }
    }
}
