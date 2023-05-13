package woowacourse.movie.ticket

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketBinding
import woowacourse.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.seat.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.utils.getParcelableCompat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketActivity : AppCompatActivity(), TicketContract.View {
    private lateinit var binding: ActivityTicketBinding
    override lateinit var presenter: TicketContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket)
        setContentView(binding.root)
        presenter = TicketPresenter(this)
        setToolbar()
        initTicketData()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.ticketToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTicketData() {
        val bookingMovie = intent.getParcelableCompat<BookingMovieEntity>(BOOKING_MOVIE_KEY)
        bookingMovie?.let { presenter.initActivity(bookingMovie) }
    }

    override fun showMovieTitle(title: String) {
        binding.ticketTitle.text = title
    }

    override fun showMovieDate(date: String) {
        binding.ticketDate.text = date
    }

    override fun showTicketInfo(seatInfo: String) {
        binding.numberOfPeople.text = seatInfo
    }

    override fun showTicketPrice(ticketPrice: String) {
        binding.ticketPrice.text = ticketPrice
    }

    override fun formatTicketDateTime(date: LocalDate, time: LocalTime): String {
        val formatDate = date.format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))
        val formatTime = time.format(DateTimeFormatter.ofPattern(getString(R.string.time_format)))
        return formatDate.plus(" $formatTime")
    }

    override fun formatTicketSeat(count: Int, seats: String, theater: String): String {
        return getString(R.string.ticket_info, count, seats, theater)
    }

    override fun formatTicketPrice(totalTicketPrice: Int): String {
        return getString(R.string.ticket_price, totalTicketPrice)
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
