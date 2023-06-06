package woowacourse.movie.presentation.activities.ticketingresult

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.databinding.ActivityTicketingResultBinding
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.model.item.Reservation

class TicketingResultActivity : AppCompatActivity(), TicketingResultContract.View {

    override lateinit var presenter: TicketingResultPresenter
    private lateinit var binding: ActivityTicketingResultBinding

    private val reservation: Reservation by lazy {
        intent.getParcelableCompat(RESERVATION_KEY)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketingResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = TicketingResultPresenter(this)
        presenter.updateMovieInformation(reservation)
        showBackButton()
    }

    override fun showReservation(reservation: Reservation) {
        binding.reservation = reservation
        binding.ticket = reservation.ticket
        binding.ticketPrice = reservation.ticketPrice
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
