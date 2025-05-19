package woowacourse.movie.ui.view.ticket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.data.local.datasource.TicketDataSourceImpl
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.reservation.Row
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.ui.view.MainActivity
import java.time.LocalDateTime

class TicketActivity :
    AppCompatActivity(),
    TicketContract.View {
    private lateinit var presenter: TicketContract.Presenter

    private lateinit var cancelDescriptionView: TextView
    private lateinit var priceView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var showtimeView: TextView
    private lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_ticket)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBackPressed()
        findViews()

        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent =
                        Intent(this@TicketActivity, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    startActivity(intent)
                    finish()
                }
            }
        onBackPressedDispatcher.addCallback(this, callback)
        val ticketId = intent.getLongExtra(EXTRA_TICKET_ID, 0L)
        val ticketDataSource: TicketDataSource =
            TicketDataSourceImpl(getMovieDatabase(this).ticketDao())
        presenter = TicketPresenter(this, ticketDataSource, ticketId)
    }

    private fun setBackPressed() {
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent =
                        Intent(this@TicketActivity, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        }
                    startActivity(intent)
                    finish()
                }
            }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun findViews() {
        cancelDescriptionView = findViewById<TextView>(R.id.tv_ticket_cancel_description)
        priceView = findViewById<TextView>(R.id.tv_ticket_price)
        descriptionView = findViewById<TextView>(R.id.tv_ticket_description)
        showtimeView = findViewById<TextView>(R.id.tv_ticket_showtime)
        titleView = findViewById<TextView>(R.id.tv_ticket_movie_title)
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
        count: Int,
        seats: Set<Seat>,
        cinemaName: String,
    ) {
        descriptionView.text =
            getString(
                R.string.ticket_description,
                count,
                seats.joinToString { it.prettyString },
                cinemaName,
            )
    }

    private val Seat.prettyString: String get() = "${row.prettyString}${column.value}"

    private val Row.prettyString: String get() = ('A' + this.value - 1).toString()

    override fun setPrice(price: Int) {
        priceView.text = getString(R.string.ticket_price, price)
    }

    companion object {
        private const val EXTRA_TICKET_ID = "woowacourse.movie.EXTRA_TICKET_ID"

        fun newIntent(
            context: Context,
            ticketId: Long,
        ): Intent = Intent(context, TicketActivity::class.java).putExtra(EXTRA_TICKET_ID, ticketId)
    }
}
