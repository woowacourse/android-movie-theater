package woowacourse.movie.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.complete.BookingCompleteActivity
import woowacourse.movie.view.core.ext.showToast
import woowacourse.movie.view.history.adapter.TicketAdapter
import woowacourse.movie.view.history.adapter.model.toItem

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var ticketAdapterActionHandler: TicketAdapter.Handler

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
        super.onViewCreated(view, savedInstanceState)
        ticketAdapterActionHandler = TickAdapterActionHandler(this)
        val presenter = BookingHistoryPresenterFactory().initialize(requireContext(), this)
        presenter.loadHistory()
    }

    override fun showTickets(tickets: List<Ticket>) {
        val items = tickets.map { it.toItem() }
        requireActivity().runOnUiThread {
            val ticketAdapter = TicketAdapter(ticketAdapterActionHandler, items)

            with(binding.rv) {
                adapter = ticketAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
            }
        }
    }

    override fun moveToTicketDetail(ticketId: Long) {
        val intent = BookingCompleteActivity.newIntent(requireContext(), ticketId)
        startActivity(intent)
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun showMessage() {
        requireActivity().runOnUiThread {
            requireContext().showToast(R.string.text_booking_history_fail)
        }
    }
}
