package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.list.adapter.ReservationHistoryAdapter
import woowacourse.movie.list.model.TicketDatabase
import woowacourse.movie.ticket.model.DbTicket
import java.util.concurrent.CountDownLatch

class ReservationHistoryFragment : Fragment() {
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter
    private lateinit var tickets: List<DbTicket>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = bindFragment(inflater, container)
        val ticketDb = TicketDatabase.getDatabase(requireContext())
        val latch = CountDownLatch(1)
        fetchAndDisplayDataFromDb(ticketDb, latch)
        latch.await()
        return binding.root
    }

    private fun fetchAndDisplayDataFromDb(
        ticketDb: TicketDatabase,
        latch: CountDownLatch
    ) {
        Thread {
            tickets = ticketDb.ticketDao().getAll()
            reservationHistoryAdapter = ReservationHistoryAdapter(tickets)
            binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
            latch.countDown()
        }.start()
    }

    private fun bindFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentReservationHistoryBinding.inflate(
        inflater,
        container,
        false,
    )
}
