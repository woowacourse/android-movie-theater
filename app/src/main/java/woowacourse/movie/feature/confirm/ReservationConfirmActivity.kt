package woowacourse.movie.feature.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationConfirmBinding
import woowacourse.movie.feature.common.BackKeyActionBarActivity
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.DateTimeFormatters
import woowacourse.movie.util.DecimalFormatters
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class ReservationConfirmActivity : BackKeyActionBarActivity() {
    private lateinit var binding: ActivityReservationConfirmBinding
    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_confirm)
        val tickets = intent.getParcelableExtraCompat<TicketsState>(KEY_TICKETS)
            ?: return keyError(KEY_TICKETS)
        initTicketsData(tickets)
    }

    private fun initTicketsData(tickets: TicketsState) {
        binding.tickets = tickets
        binding.reservationCountSeatTheater.text = getString(
            R.string.person_count_and_seat_theater,
            tickets.tickets.size,
            tickets.tickets.map { it.seatPositionState }.joinToString { it.toString() },
            tickets.theater.theaterName
        )
        binding.reservationDate.text = DateTimeFormatters.convertToDateTime(tickets.dateTime)
        binding.reservationMoney.text =
            DecimalFormatters.convertToMoneyFormat(tickets.totalDiscountedMoneyState)
    }

    companion object {
        fun getIntent(context: Context, tickets: TicketsState): Intent {
            val intent = Intent(context, ReservationConfirmActivity::class.java)
            intent.putExtra(KEY_TICKETS, tickets)
            return intent
        }

        private const val KEY_TICKETS = "key_tickets"
    }
}
