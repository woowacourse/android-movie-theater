package woowacourse.movie.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.ui.complete.BookingCompleteActivity

class BookingHistoryFragment : Fragment(), BookingHistoryContract.View {
    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding get() = _binding!!
    private val presenter: BookingHistoryContract.Presenter by lazy { BookingHistoryPresenter(this) }
    private val bookedHistoryAdapter by lazy { generateBookedHistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_booking_history, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        presenter.loadBookingHistories()
        binding.layoutRecyclerHistory.apply {
            adapter = bookedHistoryAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL),
            )
        }
    }

    override fun showHistories(bookedTickets: List<BookedTicket>) {

        bookedHistoryAdapter.submitList(bookedTickets)
    }

    override fun moveToBookedTicket(bookedTicket: BookedTicket) {
        startActivity(BookingCompleteActivity.newIntent(requireActivity(), bookedTicket))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateBookedHistoryAdapter(): BookingHistoryAdapter {
        return BookingHistoryAdapter { bookedTicket ->
            presenter.loadBookedTicket(bookedTicket)
        }
    }
}
