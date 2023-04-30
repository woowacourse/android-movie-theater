package woowacourse.movie.presentation.view.main.home.bookcomplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.R
import woowacourse.movie.presentation.view.common.BackButtonActivity

class BookCompleteActivity : BackButtonActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_complete)

        val bookingCompleteInfo =
            intent.getLongExtra(RESERVATION_ID_INTENT_KEY, -1L)
        processEmptyBookingData(bookingCompleteInfo)

        val movieBookingData = ReservationRepository.findById(bookingCompleteInfo)
        setViewData(movieBookingData!!)
    }

    private fun processEmptyBookingData(bookingCompleteInfo: Long) {
        if (bookingCompleteInfo == -1L) {
            Toast.makeText(this, getString(R.string.error_intent_message), Toast.LENGTH_SHORT)
                .show()
            this.finish()
        }
    }

    private fun setViewData(reservation: Reservation) {
        findViewById<TextView>(R.id.tv_book_movie_title).text = reservation.movieTitle
        findViewById<TextView>(R.id.tv_book_date).text =
            formatBookingTime(reservation.date, reservation.time)
        findViewById<TextView>(R.id.tv_book_person_count).text =
            getString(R.string.book_person_info).format(
                reservation.ticketCount,
                reservation.seatNames
            )
        findViewById<TextView>(R.id.tv_book_total_pay).text =
            getString(R.string.book_total_pay).format(
                reservation.totalPrice
            )
    }

    private fun formatBookingTime(date: String, time: String): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate $time"
    }

    companion object {
        const val RESERVATION_ID_INTENT_KEY = "RESERVATION_ID_INTENT_KEY"
        fun getIntent(context: Context): Intent {
            return Intent(context, BookCompleteActivity::class.java)
        }
    }
}
