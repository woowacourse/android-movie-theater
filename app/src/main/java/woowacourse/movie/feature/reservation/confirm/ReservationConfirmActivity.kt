package woowacourse.movie.feature.reservation.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.R
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.DecimalFormatters
import woowacourse.movie.feature.reservation.MovieDetailActivity.Companion.KEY_TICKETS
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class ReservationConfirmActivity : BackKeyActionBarActivity() {

    private val discountApplyUseCase = DiscountApplyUseCase()
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
                tickets.positions.size,
                tickets.positions.joinToString { it.toString() }
            )
        setDiscountApplyMoney(tickets)
    }

    private fun setDiscountApplyMoney(tickets: TicketsState) {
        val discountApplyMoney = discountApplyUseCase(tickets.asDomain())
        moneyTextView.text =
            DecimalFormatters.convertToMoneyFormat(discountApplyMoney.asPresentation())
    }

    companion object {
        fun startActivity(context: Context, tickets: TicketsState) {
            val intent = Intent(context, ReservationConfirmActivity::class.java).apply {
                putExtra(KEY_TICKETS, tickets)
            }
            context.startActivity(intent)
        }
    }
}
