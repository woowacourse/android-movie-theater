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
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.feature.reservation.ReservationActivity.Companion.TICKET
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.intentSerializable

class ReservationFinishedActivity : AppCompatActivity(), ReservationFinishedContract.View {
    private val binding: ActivityReservationFinishedBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_finished)
    }
    private lateinit var presenter: ReservationFinishedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitiy = this
        handleBackPressed()
        initPresenter()
        with(presenter) {
            handleUndeliveredTicket()
            loadMovie()
            loadTicket()
        }
    }

    override fun showMovieTitle(movie: Movie) {
        binding.movieTitle = movie.title
    }

    override fun showReservationHistory(ticket: Ticket) {
        with(binding) {
            this.ticket = ticket
            val seats = ticket.seats.seats
            headCount = seats.size
            amount = convertAmountFormat(this@ReservationFinishedActivity, ticket.amount)
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
        val intent = Intent(this@ReservationFinishedActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun initPresenter() {
        presenter = ReservationFinishedPresenter(this, ScreeningDao(), receiveTicket())
    }

    private fun receiveTicket() =
        intent.intentSerializable(TICKET, Ticket::class.java) ?: Ticket(DEFAULT_MOVIE_ID, "", Seats(), ScreeningDateTime("", ""), 0)

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            navigateToHome()
        }
    }
}
