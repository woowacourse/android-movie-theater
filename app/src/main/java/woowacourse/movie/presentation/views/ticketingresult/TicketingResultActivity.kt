package woowacourse.movie.presentation.views.ticketingresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.showBackButton
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.views.main.MainActivity
import woowacourse.movie.presentation.views.ticketingresult.contract.TicketingResultContract
import woowacourse.movie.presentation.views.ticketingresult.presenter.TicketingResultPresenter

class TicketingResultActivity : AppCompatActivity(), TicketingResultContract.View {
    override val presenter: TicketingResultContract.Presenter by lazy { makePresenter() }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            presenter.onShowMainScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing_result)
        showBackButton()
        presenter.attach(this)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun showTicketingResult(reservation: Reservation) {
        showMovieInformation(reservation)
        showPaymentPrice(reservation)
    }

    private fun showMovieInformation(reservation: Reservation) {
        val movieDate = reservation.movieDate
        val movieTime = reservation.movieTime

        findViewById<TextView>(R.id.title_tv).text = reservation.movieTitle
        findViewById<TextView>(R.id.seats_tv).text = reservation.seats.sorted().toString()
        findViewById<TextView>(R.id.date_tv).text = getString(
            R.string.book_date_time,
            movieDate.year, movieDate.month, movieDate.day, movieTime.hour, movieTime.min
        )
        findViewById<TextView>(R.id.theater_tv).text = reservation.theaterName
    }

    private fun showPaymentPrice(reservation: Reservation) {
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

    override fun showMainScreen(reservation: Reservation) {
        startActivity(MainActivity.getIntent(this, reservation))
    }

    override fun close() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.onShowMainScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makePresenter() = TicketingResultPresenter(
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
