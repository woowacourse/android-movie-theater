package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.model.data.remote.DummyMovieTheaterStorage
import woowacourse.movie.presentation.choiceSeat.ChoiceSeatActivity
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    override lateinit var presenter: BookingContract.Presenter
    private val movieId: Long by lazy { intent.getLongExtra(MOVIE_ID, -1) }
    private val theater: String by lazy {
        intent.getStringExtra(THEATER) ?: throw IllegalStateException(NULL_THEATER_ERROR)
    }
    private lateinit var movieModel: MovieModel
    private val textBookingTicketCount by lazy { findViewById<TextView>(R.id.textBookingTicketCount) }
    private val dateSpinner by lazy { findViewById<Spinner>(R.id.spinnerScreeningDate) }
    private val timeSpinner by lazy { findViewById<Spinner>(R.id.spinnerScreeningTime) }
    private val dateSpinnerAdapter by lazy {
        SpinnerAdapter<LocalDate>(this, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }
    private val timeSpinnerAdapter by lazy {
        SpinnerAdapter<LocalTime>(this, R.layout.screening_date_time_item, R.id.textSpinnerDateTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        initPresenter()
        initMovieModel()
        initAdapters()
        initDateTimes()
        restoreData(savedInstanceState)
        initBookingMovieInformationView()
        gatherClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        savedInstanceState ?: return

        with(savedInstanceState) {
            presenter = BookingPresenter(
                this@BookingActivity,
                TicketCount(getInt(TICKET_COUNT)),
                DummyMovieStorage(),
                DummyMovieTheaterStorage()
            )
            dateSpinner.setSelection(getInt(DATE_POSITION), false)
            timeSpinner.setSelection(getInt(TIME_POSITION), false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(TICKET_COUNT, presenter.getTicketCurrentCount())
            putInt(DATE_POSITION, dateSpinner.selectedItemPosition)
            putInt(TIME_POSITION, timeSpinner.selectedItemPosition)
        }
        super.onSaveInstanceState(outState)
    }

    private fun initPresenter() {
        presenter = BookingPresenter(
            view = this,
            movieStorage = DummyMovieStorage(),
            movieTheaterStorage = DummyMovieTheaterStorage()
        )
    }

    private fun initMovieModel() {
        movieModel = presenter.getMovieById(movieId).toPresentation()
    }

    private fun gatherClickListeners() {
        clickMinus()
        clickPlus()
        clickBookingComplete()
    }

    private fun initBookingMovieInformationView() {
        BookingMovieInformationView(findViewById(R.id.layout_booking_movie_information), movieModel)
    }

    private fun clickMinus() {
        findViewById<Button>(R.id.buttonBookingMinus).setOnClickListener {
            presenter.minusTicketCount()
        }
    }

    private fun clickPlus() {
        findViewById<Button>(R.id.buttonBookingPlus).setOnClickListener {
            presenter.plusTicketCount()
        }
    }

    override fun setTicketCount(ticketCount: Int) {
        textBookingTicketCount.text = ticketCount.toString()
    }

    private fun clickBookingComplete() {
        findViewById<Button>(R.id.buttonBookingChooseSeat).setOnClickListener {
            bookMovie()
        }
    }

    private fun bookMovie() {
        val dateTime = LocalDateTime.of(
            dateSpinnerAdapter.getItem(findViewById<Spinner>(R.id.spinnerScreeningDate).selectedItemPosition),
            timeSpinnerAdapter.getItem(findViewById<Spinner>(R.id.spinnerScreeningTime).selectedItemPosition)
        )
        val reservation =
            ReservationModel(movieModel.id, dateTime, presenter.getTicketCurrentCount())
        startActivity(ChoiceSeatActivity.getIntent(this, reservation))
    }

    private fun initAdapters() {
        dateSpinner.adapter = dateSpinnerAdapter
        timeSpinner.adapter = timeSpinnerAdapter
    }

    override fun setDateSpinnerItems(items: List<LocalDate>) {
        dateSpinnerAdapter.initItems(items)
    }

    override fun setTimeSpinnerItems(items: List<LocalTime>) {
        timeSpinnerAdapter.initItems(items)
    }

    private fun initDateTimes() {
        presenter.getScreeningDates(movieId)
        presenter.getScreeningTimes(movieId, theater)
    }

    companion object {
        private const val THEATER = "theater"
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"
        private const val NULL_THEATER_ERROR = "영화관 정보가 전달되지 않았습니다"

        fun getIntent(context: Context, movieId: Long, theater: String): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(THEATER, theater)
            }
        }
    }
}
