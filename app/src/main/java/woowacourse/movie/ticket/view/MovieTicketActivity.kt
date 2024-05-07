package woowacourse.movie.ticket.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTicketBinding
import woowacourse.movie.detail.view.DetailActivity
import woowacourse.movie.detail.view.DetailActivity.Companion.EXTRA_COUNT_KEY
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.seats.view.SeatsActivity.Companion.DATE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.ID_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.PRICE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.SEATS_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.TIME_KEY
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.presenter.MovieTicketPresenter

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    override val presenter: MovieTicketPresenter = MovieTicketPresenter(this)
    private lateinit var binding: ActivityMovieTicketBinding
    private var theaterId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_ticket)
        binding.ticket = this
        setContentView(binding.root)
        theaterId = intent.getLongExtra(DetailActivity.EXTRA_THEATER_ID_KEY, -1)
        processPresenterTask()
    }

    private fun processPresenterTask() {
        presenter.storeTicketCount(intent.getIntExtra(EXTRA_COUNT_KEY, 0))
        presenter.storeMovieId(intent.getLongExtra(ID_KEY, -1))
        presenter.storeScreeningDate(intent.getStringExtra(DATE_KEY) ?: "ddd")
        presenter.storeScreeningTime(intent.getStringExtra(TIME_KEY) ?: "ddd")
        presenter.storePrice(intent.getIntExtra(PRICE_KEY, 0))
        presenter.storeSeats(intent.getSerializableExtra(SEATS_KEY) as List<Seat>)
        presenter.setScreeningDateInfo()
        presenter.setScreeningTimeInfo()
        presenter.setTicketInfo()
    }

    override fun showTicketView(
        movieTitle: String,
        moviePrice: Int,
        ticketCount: Int,
        seats: List<Seat>,
    ) {
        val seatsCoordinate = seats.map { it.coordinate }
        val seat = seatsCoordinate.joinToString()
        binding.ticketTitle.text = movieTitle
        binding.ticketPrice.text = TICKET_PRICE.format(moviePrice)
        binding.ticketReservationInformation.text =
            getString(
                R.string.ticket_information_format,
                ticketCount,
                seat,
                TheaterData.theaters.first { it.id == theaterId }.name,
            )
    }

    override fun showScreeningDate(info: String) {
        binding.ticketScreeningDate.text = info
    }

    override fun showScreeningTime(info: String) {
        binding.ticketScreeningTime.text = info
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"
    }
}
