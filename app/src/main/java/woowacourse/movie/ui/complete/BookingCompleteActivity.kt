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
import woowacourse.movie.ui.MovieBookingActivity
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.intentSerializable
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

        bookingCompletePresenter.loadBookedTicket(restoreBookedTicket())
    }

    override fun showMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun showScreeningDateTime(dateTime: LocalDateTime) {
        binding.dateTime = dateTime
        binding.stringFormatter = StringFormatter
    }

    override fun showDetailInfos(
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

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
        binding.stringFormatter = StringFormatter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                moveToHome()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun restoreBookedTicket(): BookedTicket = intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)!!

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveToHome()
                }
            },
        )
    }

    private fun moveToHome() {
        val intent =
            Intent(this, MovieBookingActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        startActivity(intent)
        finish()
    }

    private fun Seat.toText(): String = Char(row + ASCII_A.code) + (col + 1).toString()

    companion object {
        fun newIntent(
            context: Context,
            bookedTicket: BookedTicket,
        ): Intent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(EXTRA_BOOKED_TICKET, bookedTicket)
            }

        private const val EXTRA_BOOKED_TICKET = "bookedTicket"
        private const val ASCII_A = 'A'
    }
}
