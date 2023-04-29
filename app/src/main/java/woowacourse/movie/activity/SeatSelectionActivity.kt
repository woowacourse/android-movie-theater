package woowacourse.movie.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import domain.Seat
import domain.Seats
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.AlarmReceiver.Companion.ALARM_CODE
import woowacourse.movie.AlarmReceiver.Companion.REQUEST_CODE
import woowacourse.movie.R
import woowacourse.movie.dto.BookingHistoryDto
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.movie.MovieTimeDto
import woowacourse.movie.dto.seat.SeatsDto
import woowacourse.movie.dto.ticket.TicketCountDto
import woowacourse.movie.mapper.seat.mapToSeats
import woowacourse.movie.mapper.seat.mapToSeatsDto
import woowacourse.movie.view.SeatSelectView
import java.time.LocalDateTime
import java.time.ZoneId

class SeatSelectionActivity : AppCompatActivity() {

    private var seats = Seats()

    private val date by lazy { intent.getSerializableExtra(DATE_KEY) as MovieDateDto }
    private val time by lazy { intent.getSerializableExtra(TIME_KEY) as MovieTimeDto }
    private val movie by lazy { intent.getSerializableExtra(MOVIE_KEY) as MovieDto }
    private val ticketCount by lazy { intent.getSerializableExtra(TICKET_KEY) as TicketCountDto }
    private val enterBtn by lazy { findViewById<TextView>(R.id.enterBtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)
        setUpState(savedInstanceState)
        setMovieTitle()
    }

    private fun setUpSeatsView() {
        SeatSelectView(
            findViewById(R.id.seat_layout),
            ::onSeatClick,
            seats,
        )
    }

    private fun setMovieTitle() {
        val movieTtile = findViewById<TextView>(R.id.movie_title)
        movieTtile.text = movie.title
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val seatsDto = savedInstanceState.getSerializable(SEATS_POSITION) as SeatsDto
            seats = seatsDto.mapToSeats()
            setPrice(seats.caculateSeatPrice(LocalDateTime.of(date.date, time.time)))
        } else {
            setPrice(0)
        }
        setUpSeatsView()
        setEnterBtnClickable()
    }

    private fun onSeatClick(seat: Seat, textView: TextView) {
        when {
            isPossibleSelect(seat, ticketCount.numberOfPeople) -> selectSeat(
                textView,
                seat,
            )
            isSeatCancelable(seat) -> unselectSeat(textView, seat)
            else -> {
                Toast.makeText(this, R.string.seats_size_over_error, Toast.LENGTH_LONG)
                    .show()
                return
            }
        }
        setPrice(seats.caculateSeatPrice(LocalDateTime.of(date.date, time.time)))
        setEnterBtnClickable()
    }

    private fun setEnterBtnClickable() {
        if (isPossibleEnter(ticketCount.numberOfPeople)) {
            enterBtn.setBackgroundColor(getColor(R.color.enter))
            onEnterBtnClickListener(enterBtn)
        } else {
            enterBtn.setBackgroundColor(getColor(R.color.not_enter))
            enterBtn.setOnClickListener(null)
        }
    }

    private fun onEnterBtnClickListener(enterBtn: TextView) {
        enterBtn.setOnClickListener {
            showBookingDialog()
        }
    }

    private fun showBookingDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.seat_dialog_title)
            .setMessage(R.string.seat_dialog_message)
            .setPositiveButton(R.string.seat_dialog_yes) { _, _ -> moveActivity() }
            .setNegativeButton(R.string.seat_dialog_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun isSeatCancelable(seat: Seat): Boolean {
        return seats.containsSeat(seat)
    }

    private fun isPossibleEnter(count: Int): Boolean {
        return seats.checkSeatSizeMatch(count)
    }

    private fun isPossibleSelect(seat: Seat, count: Int): Boolean {
        return !seats.containsSeat(seat) && seats.isPossibleSeatSize(count)
    }

    private fun selectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.select_seat))
        seats.add(seat)
    }

    private fun unselectSeat(textView: TextView, seat: Seat) {
        textView.setBackgroundColor(getColor(R.color.white))
        seats.remove(seat)
    }

    private fun setPrice(ticketPrice: Int) {
        val price = findViewById<TextView>(R.id.ticket_price)
        price.text = getString(R.string.ticket_price_seat_page, ticketPrice)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(SEATS_POSITION, seats.mapToSeatsDto())
        super.onSaveInstanceState(outState)
    }

    private fun moveActivity() {
        val bookingMovie = BookingMovieDto(movie, date, time, ticketCount, seats.mapToSeatsDto())
        val intent = Intent(this, TicketActivity::class.java)
        intent.putExtra(BOOKING_MOVIE_KEY, bookingMovie)
        putAlarm(bookingMovie)
        BookingHistoryDto.add(bookingMovie)
        startActivity(intent)
        finish()
    }

    private fun setAlarmTime(bookingMovie: BookingMovieDto): Long {
        val date = bookingMovie.date.date
        val time = bookingMovie.time.time

        val movieDateTime = LocalDateTime.of(date, time)
        val timeZone = ZoneId.of("Asia/Seoul")
        return movieDateTime.minusMinutes(30).atZone(timeZone).toInstant().toEpochMilli()
    }

    private fun putAlarm(bookingMovie: BookingMovieDto) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            setAlarmTime(bookingMovie),
            createReceiverPendingIntent(bookingMovie),
        )
    }

    private fun createReceiverPendingIntent(bookingMovie: BookingMovieDto): PendingIntent {
        return Intent(this, AlarmReceiver::class.java).let {
            it.action = ALARM_CODE
            it.putExtra(BOOKING_MOVIE_KEY, bookingMovie)
            PendingIntent.getBroadcast(
                this,
                REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }

    companion object {
        const val TICKET_KEY = "ticket"
        const val MOVIE_KEY = "movie"
        const val DATE_KEY = "movie_date"
        const val TIME_KEY = "movie_time"
        const val BOOKING_MOVIE_KEY = "booking_movie"
        const val SEATS_POSITION = "seats_position"
    }
}
