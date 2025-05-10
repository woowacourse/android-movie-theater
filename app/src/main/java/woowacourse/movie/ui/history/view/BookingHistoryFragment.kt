package woowacourse.movie.ui.history.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.ui.history.contract.BookingHistoryContract
import woowacourse.movie.ui.history.presenter.BookingHistoryPresenter

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private val bookingHistoryPresenter: BookingHistoryPresenter by lazy {
        BookingHistoryPresenter(this, AppDatabase.getInstance(requireContext()))
    }
    private var _binding: FragmentBookingHistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBookingHistoryBinding.inflate(inflater, container, false)
        bookingHistoryPresenter.loadBookedTickets()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setBookedTicketItems(items: List<BookedTicket>) {
        val adapter = BookedTicketAdapter()

        binding.bookingHistoryRecyclerView.adapter = adapter
        adapter.submitList(items)
    }
}
