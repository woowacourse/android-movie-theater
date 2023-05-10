package woowacourse.movie.ui.bookinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.ui.completed.CompletedActivity

class BookingHistoryFragment : Fragment(), BookingHistoryContract.View {

    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding: FragmentBookingHistoryBinding
        get() = _binding!!

    private val bookingHistoryPresenter: BookingHistoryPresenter by lazy {
        BookingHistoryPresenter(
            view = this,
            repository = BookingHistoryDBAdapter(BookingHistoryDBHelper(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookingHistoryBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingHistoryPresenter.initBookingHistories()
    }

    override fun initAdapter(bookingHistories: List<ReservationUiModel>) {
        val adapter = BookingHistoryAdapter(
            onBookingHistoryClicked = ::navigateToCompleteView
        )

        binding.recyclerBookingHistory.adapter = adapter
        adapter.initList(bookingHistories)
    }

    private fun navigateToCompleteView(reservationUiModel: ReservationUiModel) {
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = reservationUiModel,
        )
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
