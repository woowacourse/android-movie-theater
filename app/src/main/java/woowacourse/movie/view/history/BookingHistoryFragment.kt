package woowacourse.movie.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.room.Room
import woowacourse.movie.R
import woowacourse.movie.data.TicketDatabase
import woowacourse.movie.data.TicketEntity
import woowacourse.movie.data.TicketEntity.Companion.TICKET_TABLE_NAME
import woowacourse.movie.data.toDomain
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.history.adapter.HistoryAdapter
import woowacourse.movie.view.home.complete.BookingCompleteActivity
import kotlin.concurrent.thread

class BookingHistoryFragment : Fragment(R.layout.fragment_booking_history), BookingHistoryContract.View, BookingHistoryEventHandler {
    private var _binding: FragmentBookingHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: BookingHistoryContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookingHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL)
        binding.rv.addItemDecoration(decoration)

        presenter = BookingHistoryPresenter(this)
        val db =
            Room.databaseBuilder(
                binding.root.context,
                TicketDatabase::class.java,
                TICKET_TABLE_NAME,
            ).build()
        thread {
            val tickets = db.ticketDao().getAll().map(TicketEntity::toDomain)
            presenter.loadTickets(tickets)
        }
    }

    override fun showTickets(tickets: List<Ticket>) {
        activity?.runOnUiThread {
            binding.rv.adapter = HistoryAdapter(tickets, this)
        }
    }

    override fun onBookingSelected(ticket: Ticket) {
        presenter.selectHistory(ticket)
    }

    override fun moveToBookingComplete(ticket: Ticket) {
        val intent = BookingCompleteActivity.newIntent(requireContext(), ticket, this::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
