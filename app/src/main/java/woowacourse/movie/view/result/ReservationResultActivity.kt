package woowacourse.movie.view.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.model.ticket.toTicket
import woowacourse.movie.presenter.result.ReservationResultContract
import woowacourse.movie.presenter.result.ReservationResultPresenter
import woowacourse.movie.repository.ReservationTicketRepositoryImpl
import woowacourse.movie.utils.MovieUtils.convertAmountFormat
import woowacourse.movie.utils.MovieUtils.makeToast
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.DEFAULT_TICKET_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    private val binding: ActivityReservationResultBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
    }
    private val presenter: ReservationResultPresenter by lazy {
        ReservationResultPresenter(
            this,
            ReservationTicketRepositoryImpl(this),
            ScreeningDao(),
            TheaterDao(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.reservationFinished = this
        handleBackPressed()
        receiveTicket()
    }

    override fun showMovieTitle(movie: Movie) {
        binding.textViewReservationFinishedTitle.text = movie.title
    }

    override fun showReservationHistory(ticket: Ticket) {
        val seats = ticket.seats.seats
        binding.textViewReservationFinishedNumberOfTickets.text = seats.size.toString()
        binding.textViewReservationFinishedTicketPrice.text =
            convertAmountFormat(this, ticket.amount)
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
        binding.textViewReservationFinishedTheaterName.text =
            getString(R.string.reservation_finished_theater, theaterName)
    }

    private fun receiveTicket() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    val ticketId = intent.getLongExtra(RESERVATION_TICKET_ID, DEFAULT_TICKET_ID)
                    Log.d("ticketId",ticketId.toString())
                    val reservationTicket = presenter.loadTicketWithTicketId(ticketId)
                    reservationTicket ?: throw NoSuchElementException()
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        with(presenter) {
                            loadMovie(it.movieId)
                            loadTicket(it.toTicket())
                        }
                    }
                }.onFailure {
                    withContext(Dispatchers.Main) {
                        showErrorToast()
                        finish()
                    }
                }
            }
        }
    }

    private fun handleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@ReservationResultActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}
