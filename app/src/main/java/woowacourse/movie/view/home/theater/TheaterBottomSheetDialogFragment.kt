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
import woowacourse.movie.presenter.theater.TheaterContracts
import woowacourse.movie.presenter.theater.TheaterPresenter
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.reservation.ReservationActivity

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterContracts.View {
    private lateinit var binding: BottomSheetFragmentTheaterBinding
    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var presenter: TheaterContracts.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        presenter = TheaterPresenter(this)

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

        setupAdapter()
        presenter.updateTheaterMovieSchedules(
            arguments?.getSerializableExtraData<TheaterMovieSchedules>(
                THEATER_KEY,
            ) ?: return,
        )
    }

    private fun setupAdapter() {
        if (::theaterAdapter.isInitialized.not()) {
            theaterAdapter =
                TheaterAdapter { presenter.requestReservation(it) }
        }

        binding.theaters.adapter = theaterAdapter
    }

    override fun showTheaterMovieSchedule(theaterMovieSchedules: TheaterMovieSchedules) {
        theaterAdapter.submitList(theaterMovieSchedules.value.toList())
    }

    override fun showReservationView(theaterMovieSchedule: TheaterMovieSchedule) {
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
