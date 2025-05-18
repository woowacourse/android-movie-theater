package woowacourse.movie.ui.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.utils.StringFormatter
import java.time.LocalDateTime

class BookingCompleteActivity :
    AppCompatActivity(),
    BookingCompleteContract.View {
    private val bookingCompletePresenter = BookingCompletePresenter(this)
    private lateinit var binding: ActivityBookingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
            DataBindingUtil.setContentView(
                this@BookingCompleteActivity,
                R.layout.activity_booking_complete,
            )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        applyWindowInsets()
        setOnBackPressedCallback()

        bookingCompletePresenter.loadBookedTicket(restoreBookedTicketId())
    }

    override fun showBookedTicket(bookedTicket: BookedTicket) {
        showMovieTitle(bookedTicket.movieTitle)
        showScreeningDateTime(bookedTicket.movieSchedule.screeningDateTime)
        showDetailInfos(
            bookedTicket.headcount,
            bookedTicket.movieSchedule.seats,
            bookedTicket.theaterName,
        )
        showTotalPrice(bookedTicket.totalPrice())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    private fun showScreeningDateTime(dateTime: LocalDateTime) {
        binding.dateTime = dateTime
        binding.stringFormatter = StringFormatter
    }

    private fun showDetailInfos(
        headcount: Headcount,
        seats: Seats,
        theaterName: String,
    ) {
        val count = headcount.count
        val seatsNames = seats.reservingSeats.map { seat -> seat.toText() }.sorted().joinToString()

        binding.tvDetailInfo.text =
            getString(
                R.string.text_headcount_with_seats_and_theater,
                count, seatsNames, theaterName,
            )
    }

    private fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.stringFormatter = StringFormatter
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun restoreBookedTicketId(): Long = intent.getLongExtra(EXTRA_BOOKED_TICKET_ID, 0L)

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            },
        )
    }

    private fun Seat.toText(): String = Char(row + ASCII_A.code) + (col + 1).toString()

    companion object {
        private const val EXTRA_BOOKED_TICKET_ID = "EXTRA_BOOKED_TICKET_ID"
        private const val ASCII_A = 'A'

        fun newIntent(
            context: Context,
            bookedTicketId: Long,
        ): Intent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(EXTRA_BOOKED_TICKET_ID, bookedTicketId)
            }
    }
}
