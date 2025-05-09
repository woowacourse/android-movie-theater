package woowacourse.movie.presentation.bookinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.data.bookinghistory.BookingHistoryDatabase
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.presentation.bookingsummary.BookingSummaryActivity
import woowacourse.movie.ui.adapter.BookingHistoryAdapter

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private val presenter: BookingHistoryPresenter by lazy {
        BookingHistoryPresenter(
            this,
            BookingHistoryDatabase.getDatabase(requireContext().applicationContext)
        )
    }
    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_booking_history, container, false)
        presenter.loadBookingHistory()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showBookingHistory(tickets: List<MovieTicket>) {
        val adapter = BookingHistoryAdapter {
            presenter.selectBookingHistory(it)
        }
        adapter.submitList(tickets)
        binding.rvBookingList.apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
    }

    override fun navigateToBookingSummary(ticket: MovieTicket) {
        val intent = BookingSummaryActivity.newIntent(requireContext(), ticket)
        startActivity(intent)
    }
}