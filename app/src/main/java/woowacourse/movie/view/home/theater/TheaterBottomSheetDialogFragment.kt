package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetFragmentTheaterBinding
import woowacourse.movie.model.theater.TheaterMovieSchedule
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.presenter.theater.TheaterContracts
import woowacourse.movie.presenter.theater.TheaterPresenter
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.extension.showShortToast
import woowacourse.movie.view.reservation.ReservationActivity

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.bottom_sheet_fragment_theater),
    TheaterContracts.View {
    private var _binding: BottomSheetFragmentTheaterBinding? = null
    private val binding get() = _binding!!

    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var presenter: TheaterContracts.Presenter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = BottomSheetFragmentTheaterBinding.bind(view)
        presenter = TheaterPresenter(this)
        setupAdapter()
        presenter.updateTheaterMovieSchedules(
            arguments?.getSerializableExtraData<TheaterMovieSchedules>(
                THEATER_KEY,
            ) ?: run {
                requireContext().showShortToast("영화를 다시 선택해주세요.")
                dismiss()
                return
            },
        )
    }

    private fun setupAdapter() {
        if (::theaterAdapter.isInitialized.not()) {
            theaterAdapter =
                TheaterAdapter { presenter.onReservationRequested(it) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
