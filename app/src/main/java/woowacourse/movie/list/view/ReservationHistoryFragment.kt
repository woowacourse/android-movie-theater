package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.list.adapter.ReservationHistoryAdapter
import woowacourse.movie.list.adapter.TicketDao
import woowacourse.movie.list.contract.ReservationHistoryContract
import woowacourse.movie.list.presenter.ReservationHistoryPresenter
import woowacourse.movie.ticket.model.DbTicket
import java.io.Serializable

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    override val presenter = ReservationHistoryPresenter(this)
    private lateinit var binding: FragmentReservationHistoryBinding
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationHistoryBinding.inflate(
            inflater,
            container,
            false,
        )
        binding.reservationHistoryFragment = this
        presenter.setReservationHistoryAdapter()
        presenter.setReservationHistoryInfo()
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

    companion object {
        private const val EXTRA_DBTICKET_KEY = "dbticket_key"
        fun newFragmentInstance(tickets: Serializable): Fragment {
            return ReservationHistoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_DBTICKET_KEY, tickets)
                }
            }
        }
    }
}

@Database(entities = [DbTicket::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}
