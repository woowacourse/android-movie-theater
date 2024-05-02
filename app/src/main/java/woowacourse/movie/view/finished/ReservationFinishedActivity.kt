package woowacourse.movie.view.finished

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.view.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationFinishedBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.presenter.finished.ReservationFinishedContract
import woowacourse.movie.presenter.finished.ReservationFinishedPresenter
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.intentSerializable
import woowacourse.movie.utils.MovieUtils.makeToast
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.TICKET

class ReservationFinishedActivity : AppCompatActivity(), ReservationFinishedContract.View {
    private val binding: ActivityReservationFinishedBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_finished)
    }
    private val presenter: ReservationFinishedPresenter = ReservationFinishedPresenter(this, ScreeningDao(), TheaterDao())

    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.reservationFinished = this
        handleBackPressed()
        receiveTicket()
        with(presenter) {
            loadMovie(ticket.movieId)
            loadTicket(ticket)
        }
    }

    override fun showMovieTitle(movie: Movie) {
        binding.textViewReservationFinishedTitle.text = movie.title
    }

    override fun showReservationHistory(ticket: Ticket) {
        val seats = ticket.seats.seats
        binding.textViewReservationFinishedNumberOfTickets.text = seats.size.toString()
        binding.textViewReservationFinishedTicketPrice.text = convertAmountFormat(this, ticket.amount)
        binding.textViewReservationFinishedSeats.text =
            seats.joinToString(getString(R.string.reservation_finished_seat_separator)) { "${it.row}${it.column}" }
        presenter.loadTheater(ticket.theaterId)
        binding.textViewReservationFinishedScreeningDate.text = ticket.screeningDateTime.date
        binding.textViewReservationFinishedScreeningTime.text = ticket.screeningDateTime.time
    }

    override fun showErrorToast() {
        makeToast(this, getString(R.string.all_error))
    }

    override fun showTheaterName(theaterName: String) {
        binding.textViewReservationFinishedTheaterName.text = getString(R.string.reservation_finished_theater, theaterName)
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
