package woowacourse.movie.ui.bookinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.ui.completed.CompletedActivity

class BookingHistoryFragment : Fragment(), BookingHistoryContract.View {

    private val bookingHistoryDBHelper: BookingHistoryDBHelper by lazy {
        BookingHistoryDBHelper(requireContext())
    }
    private val bookingHistoryDBAdapter: BookingHistoryDBAdapter by lazy {
        BookingHistoryDBAdapter(bookingHistoryDBHelper)
    }
    private val bookingHistoryPresenter: BookingHistoryPresenter by lazy {
        BookingHistoryPresenter(
            view = this,
            repository = bookingHistoryDBAdapter
        )
    }
    private val recyclerView: RecyclerView by lazy {
        requireActivity().findViewById(R.id.recyclerBookingHistory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingHistoryPresenter.initBookingHistories()
    }

    override fun initAdapter(bookingHistories: List<ReservationUiModel>) {
        val adapter = BookingHistoryAdapter(::itemClicked)

        recyclerView.adapter = adapter
        adapter.initList(bookingHistories)
    }

    private fun itemClicked(reservationUiModel: ReservationUiModel) {
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = reservationUiModel,
        )
        startActivity(intent)
    }
}
