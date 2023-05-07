package woowacourse.movie.view.movieTicket

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.utils.getParcelable
import woowacourse.movie.view.main.MainActivity
import java.time.format.DateTimeFormatter

class MovieTicketActivity : AppCompatActivity(), MovieTicketContract.View {
    override lateinit var presenter: MovieTicketContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_ticket)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MovieTicketPresenter(this)

        setBackPressedCallback()
        initMovieTicketModel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        setContentView(R.layout.activity_movie_ticket)
        super.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                goBackToMainActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this) {
            goBackToMainActivity()
        }
    }

    private fun goBackToMainActivity() {
        val intent = MainActivity.createIntent(this)
        intent.flags = FLAG_ACTIVITY_CLEAR_TOP + FLAG_ACTIVITY_SINGLE_TOP
        intent.type = TYPE_MOVIE_TICKET
        startActivity(intent)
    }

    override fun initMovieTicketModel() {
        intent.getParcelable<MovieTicketModel>(TICKET_EXTRA_KEY)?.let { ticketModel ->
            presenter.setupTicketInfo(ticketModel)
        }
    }

    override fun setTextMovieTitle(title: String) {
        findViewById<TextView>(R.id.ticket_title).text = title
    }

    override fun setTextMovieDate(ticketTime: TicketTimeModel) {
        findViewById<TextView>(R.id.ticket_date).text = ticketTime.format()
    }

    override fun setTextMovieSeats(seats: Set<SeatModel>) {
        findViewById<TextView>(R.id.ticket_reserved_seats).text = getString(
            R.string.reserved_seat,
            seats.size,
            seats.sortedBy { seat -> seat.format() }
                .joinToString(", ") { seat ->
                    seat.format()
                }
        )
    }

    override fun setTextMovieTicketPrice(price: PriceModel) {
        findViewById<TextView>(R.id.ticket_price).text = price.format()
    }

    private fun TicketTimeModel.format(): String =
        dateTime.format(DateTimeFormatter.ofPattern(getString(R.string.date_time_format)))

    private fun SeatModel.format(): String = getString(R.string.seat, row.letter, column.value)

    private fun PriceModel.format(): String = getString(R.string.price, amount)

    companion object {
        private const val TICKET_EXTRA_KEY = "ticket"
        internal const val TYPE_MOVIE_TICKET = "movie_ticket"

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, MovieTicketActivity::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
