package woowacourse.movie.presentation.view.main.home.bookcomplete

import android.os.Bundle
import android.widget.Toast
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookCompleteBinding
import woowacourse.movie.presentation.view.common.BackButtonActivity

class BookCompleteActivity : BackButtonActivity() {
    private lateinit var binding: ActivityBookCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.tvBookMovieTitle.text = reservation.movieTitle
        binding.tvBookDate.text =
            formatBookingTime(reservation.date, reservation.time)
        binding.tvBookPersonCount.text =
            getString(R.string.book_person_info).format(
                reservation.ticketCount,
                reservation.seatNames
            )
        binding.tvBookTotalPay.text =
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
    }
}
