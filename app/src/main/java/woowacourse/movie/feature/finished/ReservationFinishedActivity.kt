package woowacourse.movie.feature.finished

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationFinishedBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.feature.reservation.ReservationActivity.Companion.TICKET
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.intentSerializable
import woowacourse.movie.utils.MovieUtils.makeToast

class ReservationFinishedActivity : AppCompatActivity(), ReservationFinishedContract.View {
    private val binding: ActivityReservationFinishedBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_finished)
    }
    private val presenter: ReservationFinishedPresenter = ReservationFinishedPresenter(this, ScreeningDao())

    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activitiy = this
        handleBackPressed()
        receiveTicket()
        with(presenter) {
            loadMovie(ticket.movieId)
            loadTicket(ticket)
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

    override fun showErrorToast() {
        makeToast(this, getString(R.string.all_error))
    }

    private fun receiveTicket() {
        runCatching {
            intent.intentSerializable(TICKET, Ticket::class.java) ?: throw NoSuchElementException()
        }.onSuccess {
            ticket = it
        }.onFailure {
            showErrorToast()
            finish()
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@ReservationFinishedActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}
