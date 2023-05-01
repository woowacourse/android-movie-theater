package woowacourse.movie.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.domain.usecase.DiscountApplyUseCase
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketOptState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.ui.DecimalFormatters
import woowacourse.movie.ui.confirm.ReservationConfirmActivity
import woowacourse.movie.ui.customView.ConfirmView
import woowacourse.movie.ui.receiver.AlarmReceiver
import woowacourse.movie.ui.reservation.MovieDetailActivity.Companion.KEY_TICKETS
import woowacourse.movie.util.getParcelableArrayListCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import woowacourse.movie.util.showAskDialog
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.collections.ArrayList

class SeatSelectActivity : BackKeyActionBarActivity() {
    private val discountApplyUseCase = DiscountApplyUseCase()

    private val titleTextView: TextView by lazy { findViewById(R.id.reservation_title) }
    private val moneyTextView: TextView by lazy { findViewById(R.id.reservation_money) }
    private val confirmView: ConfirmView by lazy { findViewById(R.id.reservation_confirm) }
    private lateinit var ticketOptState: TicketOptState

    private lateinit var seatTable: SeatTable

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_seat_select)

        ticketOptState =
            intent.getParcelableExtraCompat(KEY_TICKETS) ?: return keyError(KEY_TICKETS)

        titleTextView.text = ticketOptState.movieState.title

        confirmView.setOnClickListener { navigateShowDialog(seatTable.chosenSeatInfo) }
        confirmView.isClickable = false // 클릭리스너를 설정하면 clickable이 자동으로 참이 되기 때문

        seatTable = SeatTable(window.decorView.rootView, ticketOptState.countState) {
            updateSelectSeats(it)
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreState: ArrayList<SeatPositionState> =
            savedInstanceState.getParcelableArrayListCompat(SEAT_RESTORE_KEY) ?: return keyError(
                SEAT_RESTORE_KEY
            )
        seatTable.chosenSeatUpdate(restoreState.toList())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            SEAT_RESTORE_KEY,
            ArrayList(seatTable.chosenSeatInfo)
        )
    }

    private fun navigateShowDialog(seats: List<SeatPositionState>) {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) { reserveMovie(seats) }
    }

    private fun reserveMovie(seats: List<SeatPositionState>) {
        val tickets: TicketsState = TicketsState.from(ticketOptState, seats)
        TicketsRepository.addTicket(tickets)
        navigateReservationConfirmActivity(tickets)
        setNotification(tickets)
    }

    private fun navigateReservationConfirmActivity(tickets: TicketsState) {
        ReservationConfirmActivity.startActivity(this, tickets)
    }

    private fun updateSelectSeats(positionStates: List<SeatPositionState>) {
        confirmView.isClickable = (positionStates.size == ticketOptState.countState.value)

        val tickets = TicketsState(
            ticketOptState.movieState,
            ticketOptState.dateTime,
            positionStates.toList()
        )

        val discountApplyMoney = discountApplyUseCase(tickets.asDomain())
        moneyTextView.text = getString(
            R.string.discount_money,
            DecimalFormatters.convertToMoneyFormat(discountApplyMoney.asPresentation())
        )
    }

    private fun setNotification(tickets: TicketsState) {
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
            Intent(this, AlarmReceiver::class.java).apply { putExtra(AlarmReceiver.KEY_TICKETS, tickets) }.let { intent ->
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

    companion object {
        private const val SEAT_RESTORE_KEY = "seat_restore_key"

        fun startActivity(context: Context, movie: MovieState, dateTime: LocalDateTime, count: CountState) {
            val intent = Intent(context, SeatSelectActivity::class.java).apply {
                val ticketOptState = TicketOptState(movie, dateTime, count)
                putExtra(KEY_TICKETS, ticketOptState)
            }
            context.startActivity(intent)
        }
    }
}
