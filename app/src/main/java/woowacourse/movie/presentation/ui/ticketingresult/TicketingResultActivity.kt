package woowacourse.movie.presentation.ui.ticketingresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketingResultBinding
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.ui.main.MainActivity
import woowacourse.movie.presentation.ui.ticketingresult.contract.TicketingResultContract
import woowacourse.movie.presentation.ui.ticketingresult.presenter.TicketingResultPresenter

class TicketingResultActivity : AppCompatActivity(), TicketingResultContract.View {
    override lateinit var presenter: TicketingResultContract.Presenter
    private lateinit var binding: ActivityTicketingResultBinding

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            presenter.onShowMainScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticketing_result)
        presenter = makePresenter()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        showBackButton()
    }

    override fun showMainScreen(reservation: Reservation, fromMainScreen: Boolean) {
        if (!fromMainScreen) {
            startActivity(MainActivity.getIntent(this, reservation))
        }
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.onShowMainScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovieTitle(title: String) {
        binding.titleTv.text = title
    }

    override fun showTheaterName(name: String) {
        binding.theaterNameTv.text = name
    }

    override fun showTicketingDate(date: MovieDate, time: MovieTime) {
        binding.dateTv.text = getString(
            R.string.book_date_time,
            date.year, date.month, date.day, time.hour, time.min
        )
    }

    override fun showTicket(ticket: Ticket) {
        binding.ticketCountTv.text = getString(R.string.regular_count, ticket.count)
    }

    override fun showSeats(seats: PickedSeats) {
        binding.seatsTv.text = seats.sorted().toString()
    }

    override fun showTicketPrice(ticketPrice: TicketPrice) {
        binding.payResultTv.text = getString(
            R.string.movie_pay_result,
            ticketPrice.amount,
            getString(R.string.on_site_payment),
        )
    }

    private fun makePresenter(): TicketingResultPresenter = TicketingResultPresenter(
        view = this,
        reservation = intent.getParcelableCompat(RESERVATION_KEY)!!,
        fromMainScreen = intent.getBooleanExtra(FROM_KEY, true),
    )

    companion object {
        private const val RESERVATION_KEY = "reservation_key"
        private const val FROM_KEY = "from_key"

        fun getIntent(
            context: Context,
            reservation: ListItem,
            fromMainScreen: Boolean = true,
        ): Intent =
            Intent(context, TicketingResultActivity::class.java)
                .putExtra(RESERVATION_KEY, reservation)
                .putExtra(FROM_KEY, fromMainScreen)
    }
}
