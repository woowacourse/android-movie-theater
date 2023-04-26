package woowacourse.movie.ui.confirm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.ui.DateTimeFormatters
import woowacourse.movie.ui.DecimalFormatters
import woowacourse.movie.ui.reservation.MovieDetailActivity.Companion.KEY_TICKETS
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import java.time.LocalDateTime
import java.util.Calendar

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
        TicketsRepository.addTicket(tickets)
        setInitReservationData(tickets)
        setNotification(tickets)
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

    private fun setNotification(tickets: TicketsState) {
        val tickets = tickets.copy(dateTime = LocalDateTime.of(0, 4, 26, 17, 47, 30))
        val calendar: Calendar = Calendar.getInstance().apply {
            set(
                tickets.dateTime.year,
                tickets.dateTime.monthValue - 1,
                tickets.dateTime.dayOfMonth,
                tickets.dateTime.hour,
                tickets.dateTime.minute
            )
        }
        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(this, AlarmReceiver::class.java).apply { putExtra("a", tickets) }.let { intent ->
                PendingIntent.getBroadcast(
                    this,
                    tickets.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE
                )
            }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis - 30 * 60 * 1000,
            alarmIntent
        )
    }
}
