package woowacourse.movie.ticket.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketBinding
import woowacourse.movie.seat.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.ticket.view.contract.TicketContract
import woowacourse.movie.ticket.view.presenter.TicketPresenter
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
        setContentView(binding.root)
        presenter = TicketPresenter(this)
        setToolbar()
        initTicketData()
        presenter.updateTicketInfo()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.ticketToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTicketData() {
        val bookingMovieModel = intent.getParcelableCompat<BookingMovieModel>(BOOKING_MOVIE_KEY)
        bookingMovieModel?.let { presenter.getData(bookingMovieModel) }
    }

    override fun showTicketInfo(bookingMovieModel: BookingMovieModel) {
        binding.ticketTitle.text = bookingMovieModel.title
        binding.ticketDate.text = formatTicketDateTime(
            bookingMovieModel.date.date,
            bookingMovieModel.time.time
        )
        binding.numberOfPeople.text = formatTicketSeat(
            bookingMovieModel.ticketCount.numberOfPeople,
            bookingMovieModel.seats,
            bookingMovieModel.theaterName
        )
        binding.ticketPrice.text = bookingMovieModel.price.toString()
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
