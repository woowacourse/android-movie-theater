package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.list.adapter.ReservationHistoryAdapter
import woowacourse.movie.list.adapter.TicketDao
import woowacourse.movie.list.contract.ReservationHistoryContract
import woowacourse.movie.list.presenter.ReservationHistoryPresenter
import woowacourse.movie.ticket.model.DbTicket

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    override val presenter = ReservationHistoryPresenter(this)
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reservation_history,
            container,
            false,
        )
        return binding.root
    }

    override fun linkReservationHistoryAdapter(tickets: List<DbTicket>) {
        reservationHistoryAdapter = ReservationHistoryAdapter(tickets)
    }

    override fun showReservationHistoryList() {
        binding.reservationHistoryRecyclerView.adapter = reservationHistoryAdapter
    }

    override fun updateItems(items: List<DbTicket>) {
        reservationHistoryAdapter.updateItems(items)
    }
}

@Database(entities = [DbTicket::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}
