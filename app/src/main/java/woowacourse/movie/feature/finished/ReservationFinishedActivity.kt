package woowacourse.movie.feature.finished

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationFinishedBinding
import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDatabase
import woowacourse.movie.feature.history.ReservationHistoryFragment.Companion.TICKET_ID
import woowacourse.movie.feature.notification.ScreeningAlarm
import woowacourse.movie.utils.MovieUtils.convertAmountFormat

class ReservationFinishedActivity : AppCompatActivity(), ReservationFinishedContract.View {
    private val binding: ActivityReservationFinishedBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_finished)
    }
    private lateinit var presenter: ReservationFinishedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleBackPressed()
        initPresenter()
        presenter.loadTicket()
    }

    override fun showReservationHistory(ticket: Ticket) {
        with(binding) {
            this.ticket = ticket
            val seats = ticket.seats.seats
            headCount = seats.size
            amount = convertAmountFormat(this@ReservationFinishedActivity, ticket.getSeatsAmount())
            this.seats = seats.joinToString(getString(R.string.reservation_finished_seat_separator)) { "${it.row}${it.column}" }
        }
    }

    override fun showErrorSnackBar() {
        val snackBar =
            Snackbar.make(
                binding.root,
                getString(R.string.reservation_finished_error),
                Snackbar.LENGTH_INDEFINITE,
            )
        snackBar.setAction(R.string.reservation_finished_navigate_to_home) {
            snackBar.dismiss()
            navigateToHome()
        }
        snackBar.show()
    }

    override fun navigateToHome() {
        val intent =
            Intent(this@ReservationFinishedActivity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun notifyScreeningTime(ticket: Ticket) {
        ScreeningAlarm(this).generate(ticket)
    }

    private fun initPresenter() {
        val ticketDao = TicketDatabase.initialize(this).ticketDao()
        presenter =
            ReservationFinishedPresenter(
                this,
                receiveTicketId(),
                ticketDao,
            )
    }

    private fun receiveTicketId(): Long = intent.getLongExtra(TICKET_ID, -1)

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            navigateToHome()
        }
    }
}
