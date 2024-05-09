package woowacourse.movie.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.list.adapter.ReservationHistoryAdapter
import woowacourse.movie.list.adapter.ReservationOnItemClickListener
import woowacourse.movie.list.model.TicketDatabase
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.view.MovieTicketActivity
import java.util.concurrent.CountDownLatch

class ReservationHistoryFragment : Fragment(), ReservationOnItemClickListener {
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter
    private lateinit var tickets: List<DbTicket>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationHistoryBinding.inflate(inflater, container, false,)
        val ticketDb = TicketDatabase.getDatabase(requireContext())
        val latch = CountDownLatch(1)
        loadAndDisplayDataFromDb(ticketDb, latch)
        latch.await()
        return binding.root
    }

    private fun loadAndDisplayDataFromDb(
        ticketDb: TicketDatabase,
        latch: CountDownLatch
    ) {
        Thread {
            tickets = ticketDb.ticketDao().getAll()
            reservationHistoryAdapter = ReservationHistoryAdapter(tickets, this)
            binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
            latch.countDown()
        }.start()
    }

    override fun onClick(id: Long) {
        val intent = MovieTicketActivity.newTicketActivityInstance(context as Context, tickets, id)
        startActivity(intent)
    }
}
