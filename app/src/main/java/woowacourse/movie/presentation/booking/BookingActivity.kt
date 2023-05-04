package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.rules.ScreeningTimes
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.presentation.choiceSeat.ChoiceSeatActivity
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    override lateinit var presenter: BookingContract.Presenter
    private val movie: MovieModel by lazy {
        val movieId = intent.getLongExtra(MOVIE_ID, -1)
        MovieData.findMovieById(movieId).toPresentation()
    }
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
        initAdapters()
        initDateTimes()
        restoreData(savedInstanceState)
        initBookingMovieInformationView()
        gatherClickListeners()
        initDateSpinnerSelectedListener()
        initTimeSpinnerSelectedListener()
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
            presenter = BookingPresenter(this@BookingActivity, TicketCount(getInt(TICKET_COUNT)))
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
        presenter = BookingPresenter(this)
    }

    private fun gatherClickListeners() {
        clickMinus()
        clickPlus()
        clickBookingComplete()
    }

    private fun initBookingMovieInformationView() {
        BookingMovieInformationView(findViewById(R.id.layout_booking_movie_information), movie)
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
        val reservation = ReservationModel(movie.id, dateTime, presenter.getTicketCurrentCount())
        startActivity(ChoiceSeatActivity.getIntent(this, reservation))
    }

    private fun initAdapters() {
        dateSpinner.adapter = dateSpinnerAdapter
        timeSpinner.adapter = timeSpinnerAdapter
    }

    private fun initDateTimes() {
        val dates: List<LocalDate> = movie.getScreeningDates()
        val times: List<LocalTime> = ScreeningTimes.getScreeningTime(dates[0])
        dateSpinnerAdapter.initItems(dates)
        timeSpinnerAdapter.initItems(times)
    }

    private fun initDateSpinnerSelectedListener() {
        val dates: List<LocalDate> = movie.getScreeningDates()

        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertTimeItems(position)
            }

            private fun convertTimeItems(position: Int) {
                val times: List<LocalTime> = ScreeningTimes.getScreeningTime(dates[position])
                timeSpinnerAdapter.initItems(times)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun initTimeSpinnerSelectedListener() {
        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) = Unit

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"

        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}
