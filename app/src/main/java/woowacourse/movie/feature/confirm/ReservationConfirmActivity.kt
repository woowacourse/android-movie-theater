package woowacourse.movie.feature.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.feature.common.BackKeyActionBarActivity
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.DateTimeFormatters
import woowacourse.movie.util.DecimalFormatters
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class ReservationConfirmActivity : BackKeyActionBarActivity() {
    private val titleTextView: TextView by lazy { findViewById(R.id.reservation_title) }
    private val dateTextView: TextView by lazy { findViewById(R.id.reservation_date) }
    private val moneyTextView: TextView by lazy { findViewById(R.id.reservation_money) }
    private val reservationCountTextView: TextView by lazy { findViewById(R.id.reservation_count_and_seat) }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_reservation_confirm)
        val tickets = intent.getParcelableExtraCompat<TicketsState>(KEY_TICKETS)
            ?: return keyError(KEY_TICKETS)
        setInitReservationData(tickets)
    }

    private fun setInitReservationData(
        tickets: TicketsState
    ) {
        titleTextView.text = tickets.movieState.title
        dateTextView.text =
            DateTimeFormatters.convertToDateTime(tickets.dateTime)
        reservationCountTextView.text =
            getString(
                R.string.person_count_and_seat,
                tickets.tickets.size,
                tickets.tickets.map { it.seatPositionState }.joinToString { it.toString() }
            )
        setDiscountApplyMoney(tickets)
    }

    private fun setDiscountApplyMoney(tickets: TicketsState) {
        val discountApplyMoney = tickets.totalDiscountedMoneyState

        moneyTextView.text =
            DecimalFormatters.convertToMoneyFormat(discountApplyMoney)
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
