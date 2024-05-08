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
import woowacourse.movie.list.adapter.TicketDao
import woowacourse.movie.ticket.model.DbTicket

class ReservationHistoryFragment : Fragment() {
    private lateinit var binding: FragmentReservationHistoryBinding

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
}

@Database(entities = [DbTicket::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}
