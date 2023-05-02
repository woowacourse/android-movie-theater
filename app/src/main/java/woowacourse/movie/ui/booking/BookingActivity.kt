package woowacourse.movie.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MovieMapper.toUiModel
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.ui.seat.SeatActivity
import woowacourse.movie.util.formatScreenDate

class BookingActivity : AppCompatActivity(), BookingContract.View {

    private val movie: MovieUiModel by lazy {
        MovieRepository.getMovie(
            movieId = intent.getLongExtra(MOVIE_ID, -1)
        ).toUiModel()
    }
    private val dateTimeSpinner: DateTimeSpinner by lazy {
        findViewById(R.id.spinnerDateTime)
    }
    private val ticketCountText: TextView by lazy {
        findViewById(R.id.textBookingTicketCount)
    }
    private val minusButton: Button by lazy {
        findViewById(R.id.buttonBookingMinus)
    }
    private val plusButton: Button by lazy {
        findViewById(R.id.buttonBookingPlus)
    }
    private val completeButton: Button by lazy {
        findViewById(R.id.buttonBookingComplete)
    }
    private val bookingPresenter: BookingContract.Presenter by lazy {
        BookingPresenter(
            bookingView = this,
            movie = movie,
        )
    }
    private val dateTimePresenter: DateTimeContract.Presenter by lazy {
        DateTimePresenter(
            startDate = movie.startDate,
            endDate = movie.endDate,
            view = dateTimeSpinner
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        initView()
        initTicketCountText()
        initClickListeners()
        initDateTimeSpinner()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)

        setTicketCountText(
            count = savedInstanceState.getInt(TICKET_COUNT)
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run {
            putInt(TICKET_COUNT, ticketCountText.text.toString().toInt())
        }
    }

    private fun initView() {
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
        showBackButton()
    }

    private fun initTicketCountText() {
        bookingPresenter.initTicketCount()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initClickListeners() {
        initMinusBtnClickListener()
        initPlusBtnClickListener()
        initCompleteBtnClickListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMinusBtnClickListener() {
        minusButton.setOnClickListener {
            bookingPresenter.minusTicketCount()
        }
    }

    private fun initPlusBtnClickListener() {
        plusButton.setOnClickListener {
            bookingPresenter.plusTicketCount()
        }
    }

    private fun initDateTimeSpinner() {
        dateTimePresenter.initDateTimes()
    }

    override fun setTicketCountText(count: Int) {
        ticketCountText.text = count.toString()
    }

    private fun initCompleteBtnClickListener() {
        completeButton.setOnClickListener {
            val bookedMovie = BookedMovie(
                movieId = movie.id,
                theaterId = 0,
                ticketCount = ticketCountText.text.toString().toInt(),
                bookedDateTime = dateTimeSpinner.selectedDateTime
            )

            startActivity(SeatActivity.getIntent(this, bookedMovie))
            finish()
        }
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
