package woowacourse.movie.moviebooked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieBookedBinding
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater
import woowacourse.movie.helper.BuildVersion

class MovieBookedActivity : AppCompatActivity(), MovieBooked.View {
    private lateinit var binding: MovieBookedBinding
    private lateinit var presenter: MovieBookedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.movie_booked)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booked)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = MovieBookedPresenter(this)
        fetchBookingStatus()
    }

    override fun fetchBookingStatus() {
        val bookingStatus =
            BuildVersion().getParcelableClass(
                intent,
                KEY_BOOKING_STATUS,
                BookingStatus::class,
            )
        presenter.loadBookedStatus(bookingStatus)
    }

    override fun showBookedStatus(
        bookingStatus: BookingStatus,
    ) {
        binding.bookingStatus = bookingStatus
        binding.theater = bookingStatus.theater
        val seatsText =
            bookingStatus.seat.seats.joinToString { seat ->
                val rowChar = 'A' + seat.row.value
                val colNumber = seat.col.value + 1
                "$rowChar$colNumber"
            }
        binding.bookingSeat.text = seatsText
    }

    companion object {
        private const val KEY_BOOKING_STATUS = "bookingStatus"

        fun movieBookedIntent(
            context: Context,
            bookingStatus: BookingStatus,
        ): Intent {
            return Intent(context, MovieBookedActivity::class.java)
                .apply {
                    putExtra(KEY_BOOKING_STATUS, bookingStatus)
                }
        }
    }
}
