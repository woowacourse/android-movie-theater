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
import woowacourse.movie.domain.ReservationInfo
import woowacourse.movie.domain.Theater
import woowacourse.movie.helper.BuildVersion

class MovieBookedActivity : AppCompatActivity(), MovieBookedContract.View {
    private lateinit var binding: MovieBookedBinding
    private lateinit var presenter: MovieBookedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        applyWindowInserts()
        presenter = MovieBookedPresenter(this)
        fetchReservationInfo()
//        fetchBookingStatus()
    }

    override fun fetchBookingStatus() {
        val bookingStatus =
            BuildVersion().getParcelableClass(
                intent,
                KEY_BOOKING_STATUS,
                BookingStatus::class,
            )
        val theater = BuildVersion().getParcelableClass(intent, KEY_THEATER, Theater::class)
        presenter.loadBookedStatus(bookingStatus, theater)
    }

    override fun showBookedStatus(
        bookingStatus: BookingStatus,
        theater: Theater,
    ) {
//        binding.bookingStatus = bookingStatus
//        binding.theater = theater
//        val seatsText =
//            bookingStatus.seat.seats.joinToString { seat ->
//                val rowChar = 'A' + seat.row.value
//                val colNumber = seat.column.value + 1
//                "$rowChar$colNumber"
//            }
//        binding.bookedBookingSeat.text = seatsText
    }

    override fun fetchReservationInfo() {
        val reservationInfo =
            BuildVersion().getParcelableClass(
                intent,
                KEY_RESERVATION_INFO,
                ReservationInfo::class,
            )
        presenter.loadReservationInfo(reservationInfo)
    }

    override fun showReservationInfo(reservationInfo: ReservationInfo) {
        binding.reservationInfo = reservationInfo
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.movie_booked)
    }

    private fun applyWindowInserts() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booked_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val KEY_BOOKING_STATUS = "bookingStatus"
        private const val KEY_THEATER = "theater"
        private const val KEY_RESERVATION_INFO = "reservationInfo"

        fun movieBookedIntent(
            otherActivity: Context,
            bookingStatus: BookingStatus,
            theater: Theater,
        ): Intent {
            return Intent(otherActivity, MovieBookedActivity::class.java)
                .apply {
                    putExtra(KEY_BOOKING_STATUS, bookingStatus)
                    putExtra(KEY_THEATER, theater)
                }
        }

        fun newIntent(
            context: Context,
            reservationInfo: ReservationInfo,
        ): Intent {
            return Intent(context, MovieBookedActivity::class.java)
                .apply {
                    putExtra(KEY_RESERVATION_INFO, reservationInfo)
                }
        }
    }
}
