package woowacourse.movie.ui.complete.view

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
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.ui.MainActivity
import woowacourse.movie.ui.complete.contract.BookingCompleteContract
import woowacourse.movie.ui.complete.presenter.BookingCompletePresenter
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDateTime

class BookingCompleteActivity :
    AppCompatActivity(),
    BookingCompleteContract.View {
    private lateinit var binding: ActivityBookingCompleteBinding
    private val bookingCompletePresenter =
        BookingCompletePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_complete)

        applyWindowInsets()
        initializeFromIntent()
        bookingCompletePresenter.updateViews()
        setOnBackPressedCallback()
    }

    private fun initializeFromIntent() {
        val bookedTicket =
            intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)
                ?: BookedTicket(
                    DEFAULT_MOVIE_TITLE,
                    Headcount(),
                    LocalDateTime.MIN,
                    Seats(),
                    DEFAULT_THEATER_NAME,
                )
        bookingCompletePresenter.loadBookedTicket(bookedTicket)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                moveToMovieListActivity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun setBookedTicket(bookedTicket: BookedTicket) {
        binding.tvHeadcount.isSelected = true

        with(bookedTicket) {
            binding.tvReleaseDate.text = StringFormatter.dateTimeFormat(dateTime)
            binding.tvTitle.text = movieName
            binding.tvHeadcount.text =
                getString(R.string.text_headcount_with_seats_and_theater).format(
                    headcount.count,
                    seats.seats
                        .map { it.toText() }
                        .sorted()
                        .joinToString(),
                    theaterName,
                )
        }
    }

    override fun setBookedTicketPrice(price: Int) {
        val priceFormat: String = StringFormatter.thousandFormat(price)
        binding.tvPrice.text = getString(R.string.text_on_site_payment).format(priceFormat)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun moveToMovieListActivity() {
        val intent =
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        startActivity(intent)
        finish()
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveToMovieListActivity()
                }
            },
        )
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

        private const val DEFAULT_MOVIE_TITLE = "DEFAULT_MOVIE_TITLE"
        private const val DEFAULT_THEATER_NAME = "DEFAULT_THEATER_NAME"
    }
}
