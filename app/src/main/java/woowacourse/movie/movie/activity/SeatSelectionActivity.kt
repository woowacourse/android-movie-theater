package woowacourse.movie.movie.activity

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import domain.Seat
import domain.Seats
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatSelectionBinding
import woowacourse.movie.movie.alarm.AlarmReceiver
import woowacourse.movie.movie.alarm.PendingIntentBuilder
import woowacourse.movie.movie.dto.BookingHistoryDto
import woowacourse.movie.movie.dto.movie.BookingMovieDto
import woowacourse.movie.movie.dto.movie.MovieDateDto
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.movie.MovieTimeDto
import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.seat.mapToSeats
import woowacourse.movie.movie.mapper.seat.mapToSeatsDto
import woowacourse.movie.movie.utils.getParcelableCompat
import woowacourse.movie.movie.view.SeatSelectView
import java.time.LocalDateTime
import java.time.ZoneId

class SeatSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatSelectionBinding

    private var seats = Seats()

    private val date by lazy { intent.getParcelableCompat<MovieDateDto>(DATE_KEY)!! }
    private val time by lazy { intent.getParcelableCompat<MovieTimeDto>(TIME_KEY)!! }
    private val movie by lazy { intent.getParcelableCompat<MovieDto>(MOVIE_KEY)!! }
    private val ticketCount by lazy { intent.getParcelableCompat<TicketCountDto>(TICKET_KEY)!! }
    private val enterBtn by lazy { findViewById<TextView>(R.id.enterBtn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpState(savedInstanceState)
        setMovieTitle()
    }

    private fun setUpSeatsView() {
        SeatSelectView(
            binding.seatLayout,
            ::onSeatClick,
            seats,
        )
    }

    private fun setMovieTitle() {
        binding.movieTitle.text = movie.title
    }

    private fun setUpState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val seatsDto = savedInstanceState.getParcelableCompat<SeatsDto>(SEATS_POSITION)!!
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
        binding.ticketPrice.text = getString(R.string.ticket_price_seat_page, ticketPrice)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SEATS_POSITION, seats.mapToSeatsDto())
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

    private fun setDateTime(bookingMovie: BookingMovieDto): LocalDateTime {
        val dateTime = LocalDateTime.of(bookingMovie.date.date, bookingMovie.time.time)
        return dateTime.minusMinutes(1L)
    }

    private fun putAlarm(bookingMovie: BookingMovieDto) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            setDateTime(bookingMovie).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            PendingIntentBuilder(this).createReceiverPendingIntent(Intent(this, AlarmReceiver::class.java), bookingMovie),
        )
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
