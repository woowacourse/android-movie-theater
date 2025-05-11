package woowacourse.movie.view.ticket

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.contract.ticket.ReservationDetailContract
import woowacourse.movie.domain.reservation.Row
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.presenter.ticket.ReservationDetailPresenter
import woowacourse.movie.view.util.ErrorMessage
import java.time.LocalDateTime

class ReservationDetailActivity :
    AppCompatActivity(),
    ReservationDetailContract.View {
    private var presenter: ReservationDetailContract.Presenter? = null

    private lateinit var cancelDescriptionView: TextView
    private lateinit var priceView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var showtimeView: TextView
    private lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservation_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_reservation_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initPresenter()
        initViews()
    }

    private fun initPresenter() {
        val ticket =
            intent?.getTicketExtra(EXTRA_TICKET) ?: error(
                ErrorMessage(CAUSE_TICKET).notProvided(),
            )

        presenter = ReservationDetailPresenter(this, ticket)
    }

    private fun findViews() {
        cancelDescriptionView = findViewById<TextView>(R.id.tv_ticket_cancel_description)
        priceView = findViewById<TextView>(R.id.tv_ticket_price)
        descriptionView = findViewById<TextView>(R.id.tv_ticket_description)
        showtimeView = findViewById<TextView>(R.id.tv_ticket_showtime)
        titleView = findViewById<TextView>(R.id.tv_ticket_movie_title)
    }

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Reservation? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(
                    key,
                    Reservation::class.java,
                )

            else -> getSerializableExtra(key) as? Reservation
        }

    private fun initViews() {
        (presenter ?: error(ErrorMessage(CAUSE_TICKET).notProvided())).run {
            presentCancelDescription()
            presentTitle()
            presentShowtime()
            presentCount()
            presentPrice()
        }
    }

    override fun setCancelDescription(minutes: Int) {
        cancelDescriptionView.text =
            getString(
                R.string.ticket_cancel_time_description,
                minutes,
            )
    }

    override fun setMovieTitle(movieTitle: String) {
        titleView.text = movieTitle
    }

    override fun setShowtime(showtime: LocalDateTime) {
        showtimeView.text =
            showtime.run {
                getString(R.string.ticket_showtime, year, monthValue, dayOfMonth, hour, minute)
            }
    }

    override fun setCount(
        seats: Set<Seat>,
        cinemaName: String,
    ) {
        descriptionView.text =
            getString(
                R.string.ticket_description,
                seats.size,
                seats.map { it.prettyString }.sorted().joinToString(),
                cinemaName,
            )
    }

    private val Seat.prettyString: String get() = "${row.prettyString}${column.value}"

    private val Row.prettyString: String get() = ('A' + this.value - 1).toString()

    override fun setPrice(price: Int) {
        priceView.text = getString(R.string.ticket_price, price)
    }

    companion object {
        private const val CAUSE_TICKET = "ticket"
        private const val CAUSE_SEATS = "seats"
        private const val CAUSE_CINEMA = "cinemaName"

        private const val EXTRA_TICKET = "woowacourse.movie.EXTRA_TICKET"

        fun newIntent(
            context: Context,
            reservation: Reservation,
        ): Intent =
            run {
                Intent(context, ReservationDetailActivity::class.java)
                    .putExtra(EXTRA_TICKET, reservation)
            }
    }
}
