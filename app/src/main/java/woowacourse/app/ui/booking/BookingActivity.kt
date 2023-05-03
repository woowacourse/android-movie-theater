package woowacourse.app.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.app.model.BookedMovie
import woowacourse.app.model.main.MovieMapper.toUiModel
import woowacourse.app.model.main.MovieUiModel
import woowacourse.app.ui.seat.SeatActivity
import woowacourse.app.usecase.movie.MovieUseCase
import woowacourse.app.util.formatScreenDate
import woowacourse.app.util.shortToast
import woowacourse.data.movie.MovieRepositoryImpl
import woowacourse.domain.movie.ScreeningDate
import woowacourse.domain.ticket.TicketCount
import woowacourse.movie.R
import java.time.LocalDate
import java.time.LocalTime

class BookingActivity : AppCompatActivity() {
    private var ticketCount = TicketCount()
    private val dateTimeSpinner by lazy {
        findViewById<DateTimeSpinner>(R.id.spinnerDateTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val movie = getMovie()
        initDateTimes(movie)
        restoreData(savedInstanceState)
        initView(movie)
        gatherClickListeners(movie)
        initSpinnerListener()
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            ticketCount = TicketCount(savedInstanceState.getInt(TICKET_COUNT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(TICKET_COUNT, ticketCount.value)
        }
        super.onSaveInstanceState(outState)
    }

    private fun getMovie(): MovieUiModel {
        val movieId = intent.getLongExtra(MOVIE_ID, -1)
        val movieUiModel = MovieUseCase(MovieRepositoryImpl()).getMovie(movieId)?.toUiModel()
        if (movieUiModel == null) {
            shortToast(R.string.error_no_such_movie)
            finish()
        }
        return movieUiModel!!
    }

    private fun gatherClickListeners(movie: MovieUiModel) {
        clickMinus()
        clickPlus()
        clickBookingComplete(movie)
    }

    private fun initView(movie: MovieUiModel) {
        findViewById<ImageView>(R.id.imageBookingPoster).setImageResource(movie.poster)
        findViewById<TextView>(R.id.textBookingTitle).text = movie.title
        findViewById<TextView>(R.id.textBookingScreeningDate).text =
            getString(
                R.string.screening_date,
                movie.startDate.formatScreenDate(),
                movie.endDate.formatScreenDate(),
            )
        findViewById<TextView>(R.id.textBookingRunningTime).text =
            getString(R.string.running_time, movie.runningTime)
        findViewById<TextView>(R.id.textBookingDescription).text = movie.description
        findViewById<TextView>(R.id.textBookingTicketCount).text = ticketCount.value.toString()
        showBackButton()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clickMinus() {
        findViewById<Button>(R.id.buttonBookingMinus).setOnClickListener {
            ticketCount = ticketCount.minus()
            findViewById<TextView>(R.id.textBookingTicketCount).text = ticketCount.value.toString()
        }
    }

    private fun clickPlus() {
        findViewById<Button>(R.id.buttonBookingPlus).setOnClickListener {
            ticketCount = ticketCount.plus()
            findViewById<TextView>(R.id.textBookingTicketCount).text = ticketCount.value.toString()
        }
    }

    private fun clickBookingComplete(movie: MovieUiModel) {
        findViewById<Button>(R.id.buttonBookingComplete).setOnClickListener {
            val dateTime = dateTimeSpinner.selectedDateTime
            val bookedMovie =
                BookedMovie(movie.id, 0, ticketCount.value, dateTime)
            startActivity(SeatActivity.getIntent(this, bookedMovie))
            finish()
        }
    }

    private fun initDateTimes(movie: MovieUiModel) {
        val dates: List<LocalDate> = movie.screeningDates
        val times: List<LocalTime> = ScreeningDate(movie.startDate).screeningTimes
        dateTimeSpinner.initDateItems(dates)
        dateTimeSpinner.initTimeItems(times)
    }

    private fun initSpinnerListener() {
        dateTimeSpinner.initDateSelectedListener { ScreeningDate(it).screeningTimes }
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"

        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}
