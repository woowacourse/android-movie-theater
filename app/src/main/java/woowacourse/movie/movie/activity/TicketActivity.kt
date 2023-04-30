package woowacourse.movie.movie.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketBinding
import woowacourse.movie.movie.activity.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.seat.mapToSeats
import woowacourse.movie.movie.utils.getParcelableCompat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()

        Log.d("test", "인텐트 값 받아오기 시작")
        val bookingMovie = intent.getParcelableCompat<BookingMovieEntity>(BOOKING_MOVIE_KEY)!!
        Log.d("test", "인텐트 값 받아오기 성공")

        showTicketInfo(
            bookingMovie.movie,
            bookingMovie.date.date,
            bookingMovie.time.time,
        )
        showTicketInfo(
            bookingMovie.ticketCount,
            bookingMovie.seats,
        )
        showTicketPrice(
            bookingMovie.seats,
            bookingMovie.date.date,
            bookingMovie.time.time,
        )
    }

    private fun setToolbar() {
        setSupportActionBar(binding.ticketToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun formatMovieDateTime(date: LocalDate, time: LocalTime): String {
        val formatDate = date.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val formatTime = time.format(DateTimeFormatter.ofPattern(getString(R.string.time_format)))

        return formatDate.plus(" $formatTime")
    }

    private fun showTicketInfo(movie: MovieDto, date: LocalDate, time: LocalTime) {
        binding.ticketTitle.text = movie.title
        binding.ticketDate.text = formatMovieDateTime(date, time)
    }

    private fun showTicketInfo(ticket: TicketCountDto, seats: SeatsDto) {
        binding.numberOfPeople.text =
            getString(R.string.ticket_info, ticket.numberOfPeople, seats.getSeatsPositionToString())
    }

    private fun showTicketPrice(seats: SeatsDto, date: LocalDate, time: LocalTime) {
        val totalTicketPrice = seats.mapToSeats().caculateSeatPrice(LocalDateTime.of(date, time))

        binding.ticketPrice.text = getString(R.string.ticket_price, totalTicketPrice)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
