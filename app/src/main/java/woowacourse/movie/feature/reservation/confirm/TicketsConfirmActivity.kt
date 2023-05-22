package woowacourse.movie.feature.reservation.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationConfirmBinding
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.DecimalFormatters
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class TicketsConfirmActivity : BackKeyActionBarActivity() {

    private lateinit var binding: ActivityReservationConfirmBinding
    private lateinit var tickets: TicketsState

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_reservation_confirm)
        init()
        setContents()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_confirm)
        tickets = intent.getParcelableExtraCompat(KEY_TICKETS) ?: return keyError(KEY_TICKETS)
    }

    private fun setContents() {
        binding.tickets = tickets
        binding.reservationCountAndSeat.text = getString(
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
        private const val KEY_TICKETS = "tickets_key"

        fun startActivity(context: Context, tickets: TicketsState) {
            val intent = Intent(context, TicketsConfirmActivity::class.java).apply {
                putExtra(KEY_TICKETS, tickets)
            }
            context.startActivity(intent)
        }
    }
}
