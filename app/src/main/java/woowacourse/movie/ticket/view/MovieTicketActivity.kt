package woowacourse.movie.ticket.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.detail.model.Count
import woowacourse.movie.list.model.TicketDatabase.Companion.getDatabase
import woowacourse.movie.list.view.TheaterBottomSheetFragment.Companion.EXTRA_MOVIE_ID_KEY
import woowacourse.movie.list.view.TheaterBottomSheetFragment.Companion.EXTRA_THEATER_ID_KEY
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.seats.view.SeatsActivity.Companion.DATE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.PRICE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.SEATS_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.TIME_KEY
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.presenter.MovieTicketPresenter
import woowacourse.movie.util.IntentUtil.getSerializableCountData

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    override val presenter: MovieTicketPresenter = MovieTicketPresenter(this)
    private lateinit var binding: ActivityMovieTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_ticket)
        binding.ticket = this
        setContentView(binding.root)
        processPresenterTask()
    }

    override fun storeTicketInDb(ticket: DbTicket) {
        val ticketDb = getDatabase(applicationContext)
        Thread {
            ticketDb.ticketDao().insertAll(ticket)
        }.start()
    }

    private fun processPresenterTask() {
        presenter.storeTicketData(
            intent.getLongExtra(EXTRA_MOVIE_ID_KEY, -1),
            intent.getStringExtra(DATE_KEY) ?: "",
            intent.getStringExtra(TIME_KEY) ?: "",
            getSerializableCountData(intent),
            intent.getSerializableExtra(SEATS_KEY) as List<Seat>,
            intent.getLongExtra(EXTRA_THEATER_ID_KEY, -1),
            intent.getIntExtra(PRICE_KEY, 0),
        )
        presenter.setTicketInfo()
        presenter.storeTicketInDb()
    }

    override fun showTicketView(
        movieTitle: String,
        screeningDate: String,
        screeningTime: String,
        seatsCount: Count,
        seats: List<Seat>,
        theater: String,
        moviePrice: Int,
    ) {
        val seatsFormatted = seats.joinToString { it.coordinate }
        binding.ticketTitle.text = movieTitle
        binding.ticketScreeningDate.text = screeningDate
        binding.ticketScreeningTime.text = screeningTime
        binding.ticketReservationInformation.text =
            getString(
                R.string.ticket_information_format,
                seatsCount.number,
                seatsFormatted,
                theater,
            )
        binding.ticketPrice.text = TICKET_PRICE.format(moviePrice)
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"
    }
}
