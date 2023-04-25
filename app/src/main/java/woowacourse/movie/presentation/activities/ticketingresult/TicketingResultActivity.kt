package woowacourse.movie.presentation.activities.ticketingresult

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.model.Reservation

class TicketingResultActivity : AppCompatActivity() {
    private val reservation: Reservation by lazy {
        intent.getParcelableCompat(RESERVATION_KEY)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing_result)
        showBackButton()
        showTicketingResult()
    }

    private fun showTicketingResult() {
        showMovieInformation()
        showPaymentPrice()
    }

    private fun showMovieInformation() {
        val movieDate = reservation.movieDate
        val movieTime = reservation.movieTime

        findViewById<TextView>(R.id.title_tv).text = reservation.movieTitle
        findViewById<TextView>(R.id.seats_tv).text = reservation.seats.sorted().toString()
        findViewById<TextView>(R.id.date_tv).text = getString(
            R.string.book_date_time,
            movieDate.year, movieDate.month, movieDate.day, movieTime.hour, movieTime.min
        )
    }

    private fun showPaymentPrice() {
        val ticket = reservation.ticket
        val ticketPrice = reservation.ticketPrice

        findViewById<TextView>(R.id.regular_count_tv).text =
            getString(R.string.regular_count, ticket.count)
        findViewById<TextView>(R.id.pay_result_tv).text = getString(
            R.string.movie_pay_result,
            ticketPrice.amount,
            getString(R.string.on_site_payment)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        internal const val RESERVATION_KEY = "reservation"
    }
}
