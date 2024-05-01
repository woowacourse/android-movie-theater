package woowacourse.movie.ticket.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.seats.view.SeatsActivity.Companion.DATE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.ID_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.PRICE_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.SEATS_KEY
import woowacourse.movie.seats.view.SeatsActivity.Companion.TIME_KEY
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.presenter.MovieTicketPresenter
import woowacourse.movie.util.IntentUtil.getSerializableCountData

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    private lateinit var title: TextView
    private lateinit var screeningDate: TextView
    private lateinit var screeningTime: TextView
    private lateinit var price: TextView
    private lateinit var reservationInformation: TextView

    override val presenter: MovieTicketPresenter = MovieTicketPresenter(this)
    private var theaterId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_ticket)
        theaterId = intent.getLongExtra("threater_id_key", -1)
        initViewById()
        processPresenterTask()
    }

    private fun processPresenterTask() {
        presenter.storeTicketCount(getSerializableCountData(intent))
        presenter.storeMovieId(intent.getLongExtra(ID_KEY, -1))
        presenter.storeScreeningDate(intent.getStringExtra(DATE_KEY) ?: "ddd")
        presenter.storeScreeningTime(intent.getStringExtra(TIME_KEY) ?: "ddd")
        presenter.storePrice(intent.getIntExtra(PRICE_KEY, 0))
        presenter.storeSeats(intent.getSerializableExtra(SEATS_KEY) as List<Seat>)
        presenter.setScreeningDateInfo()
        presenter.setScreeningTimeInfo()
        presenter.setTicketInfo()
    }

    private fun initViewById() {
        title = findViewById(R.id.ticket_title)
        screeningDate = findViewById(R.id.ticket_screening_date)
        screeningTime = findViewById(R.id.ticket_screening_time)
        price = findViewById(R.id.ticket_price)
        reservationInformation = findViewById(R.id.ticket_reservation_information)
    }

    override fun showTicketView(
        movieTitle: String,
        moviePrice: Int,
        ticketCount: Int,
        seats: List<Seat>,
    ) {
        val seatsCoordinate = seats.map { it.coordinate }
        val seat = seatsCoordinate.joinToString()
        title.text = movieTitle
        price.text = TICKET_PRICE.format(moviePrice)
        reservationInformation.text =
            getString(
                R.string.ticket_information_format,
                ticketCount,
                seat,
                TheaterData.theaters.first { it.id == theaterId }.name,
            )
    }

    override fun showScreeningDate(info: String) {
        screeningDate.text = info
    }

    override fun showScreeningTime(info: String) {
        screeningTime.text = info
    }

    companion object {
        private const val TICKET_PRICE = "%,d원 (현장결제)"
        private const val TICKET_COUNT = "일반 %d명"
    }
}
