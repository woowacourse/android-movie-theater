package woowacourse.movie.presentation.activities.ticketing.seatpicker

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.extensions.showToast
import woowacourse.movie.presentation.extensions.title
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.receiver.ReservationPushReceiver

class SeatPickerActivity : AppCompatActivity(), SeatPickerContract.View {
    override lateinit var presenter: SeatPickerPresenter

    private val seatRowSize: Int = 5
    private val seatColSize: Int = 4

    private val movieDate by lazy {
        intent.getParcelableCompat<MovieDate>(TicketingActivity.MOVIE_DATE_KEY)!!
    }
    private val movieTime by lazy {
        intent.getParcelableCompat<MovieTime>(TicketingActivity.MOVIE_TIME_KEY)!!
    }
    private val ticket by lazy { intent.getParcelableCompat<Ticket>(TicketingActivity.TICKET_KEY)!! }
    private val movie by lazy { intent.getParcelableCompat<Movie>(HomeFragment.MOVIE_KEY)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SeatPickerPresenter(this)
        restoreState(savedInstanceState)

        setContentView(R.layout.activity_seat_picker)
        initView()
    }

    private fun initView() {
        showBackButton()
        initViewClickListener()
        presenter.updateMovieTitle()
        updateBottomView()

        initSeatTable(seatRowSize, seatColSize)
    }

    override fun setMovieTitle() {
        findViewById<TextView>(R.id.movie_title_tv).text = movie.title
    }

    override fun setDoneBtnEnabled(isEnabled: Boolean) {
        findViewById<TextView>(R.id.done_btn).isEnabled = isEnabled
    }

    override fun setTotalPriceView(ticketPrice: TicketPrice) {
        findViewById<TextView>(R.id.total_price_tv).text =
            getString(R.string.movie_pay_price, ticketPrice.amount)
    }

    private fun initViewClickListener() {
        findViewById<TextView>(R.id.done_btn).setOnClickListener { presenter.onConfirmButtonClick() }
    }

    private fun canPick(): Boolean = presenter.canPick(ticket)

    private fun initSeatTable(rowSize: Int, colSize: Int) {
        SeatRow.make(rowSize).forEach { seatRow ->
            findViewById<TableLayout>(R.id.seat_table).addView(makeSeatTableRow(seatRow, colSize))
        }
    }

    private fun makeSeatTableRow(row: SeatRow, colSize: Int): TableRow = TableRow(this).apply {
        SeatColumn.make(colSize).forEach { col ->
            addView(makeSeatView(row, col))
        }
    }

    private fun makeSeatView(row: SeatRow, col: SeatColumn): View {
        val seat = Seat(row, col)
        return seat.makeView(this, isPicked(seat)) {
            when {
                isPicked(seat) -> unpick(this, seat)
                canPick() -> pick(this, seat)
                else -> showToast(getString(R.string.exceed_pickable_seat))
            }
        }
    }

    private fun pick(seatView: View, seat: Seat) {
        presenter.pick(seat)
        updateToggledSeatResultView(seatView, true)
    }

    private fun unpick(seatView: View, seat: Seat) {
        presenter.unPick(seat)
        updateToggledSeatResultView(seatView, false)
    }

    private fun updateToggledSeatResultView(seatView: View, isPicked: Boolean) {
        updateBottomView()
        seatView.findViewById<TextView>(R.id.seat_number_tv).isSelected = isPicked
    }

    private fun updateBottomView() {
        presenter.updateDoneBtnEnabled(!canPick())
        presenter.updateTotalPriceView(presenter.calculateTotalPrice(movieDate, movieTime))
    }

    private fun isPicked(seat: Seat): Boolean {
        return presenter.isPicked(seat)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showTicketingConfirmDialog() {
        createAlertDialog(false) {
            title(getString(R.string.ticketing_confirm_title))
            message(getString(R.string.ticketing_confirm_message))
            positiveButton(getString(R.string.ticketing_confirm_positive_btn)) {
                startTicketingResultActivity()
            }
            negativeButton(getString(R.string.ticketing_confirm_negative_btn)) { it.dismiss() }
        }.show()
    }

    private fun startTicketingResultActivity() {
        val reservation = Reservation(
            movieTitle = movie.title,
            movieDate = movieDate,
            movieTime = movieTime,
            ticket = ticket,
            seats = presenter.getPickedSeats(),
            ticketPrice = presenter.calculateTotalPrice(movieDate, movieTime),
        )

        registerPushBroadcast(reservation)

        startActivity(
            Intent(this@SeatPickerActivity, TicketingResultActivity::class.java)
                .putExtra(TicketingResultActivity.RESERVATION_KEY, reservation),
        )
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PICKED_SEATS_KEY, presenter.getPickedSeats())
    }

    private fun restoreState(instanceState: Bundle?) {
        instanceState?.getParcelableCompat<PickedSeats>(PICKED_SEATS_KEY)?.let { restoredSeats ->
            presenter.setPickedSeats(restoredSeats)
        }
    }

    private fun registerPushBroadcast(reservation: Reservation) {
        val alarmIntent = Intent(this, ReservationPushReceiver::class.java)
        val pendingIntent = alarmIntent.let { intent ->
            intent.action = PushAlarmManager.PUSH_ACTION
            intent.putExtra(PushAlarmManager.PUSH_DATA_KEY, reservation)
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        PushAlarmManager.set(this, pendingIntent, reservation.reservedTime, REMINDER_TIME_MINUTES_AGO)
    }

    companion object {
        internal const val PICKED_SEATS_KEY = "picked_seats"
        internal const val REMINDER_TIME_MINUTES_AGO = 30L
    }
}
