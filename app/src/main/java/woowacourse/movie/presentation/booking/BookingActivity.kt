package woowacourse.movie.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.presentation.choiceSeat.ChoiceSeatActivity
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.util.formatDotDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    override val movieId: Long by lazy {
        intent.getLongExtra(MOVIE_ID, -1)
    }

    override val presenter: BookingContract.Presenter by lazy {
        BookingPresenter(this)
    }

    private val movieModel: MovieModel by lazy {
        presenter.requireMovieModel(movieId)
    }

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

        initAdapters()
        restoreData(savedInstanceState)
        initView()
        initDateTimes()
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
            setTicketCount(getInt(TICKET_COUNT))
            dateSpinner.setSelection(getInt(DATE_POSITION), false)
            timeSpinner.setSelection(getInt(TIME_POSITION), false)
        }
    }

    override fun setTicketCount(count: Int) {
        findViewById<TextView>(R.id.textBookingTicketCount).text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(
                TICKET_COUNT,
                findViewById<TextView>(R.id.textBookingTicketCount).text.toString().toInt(),
            )
            putInt(DATE_POSITION, dateSpinner.selectedItemPosition)
            putInt(TIME_POSITION, timeSpinner.selectedItemPosition)
        }
        super.onSaveInstanceState(outState)
    }

    private fun gatherClickListeners() {
        clickMinus()
        clickPlus()
        clickBookingComplete()
    }

    private fun initView() {
        setMoviePoster()
        setMovieTitle()
        setMovieScreeningDate()
        setMovieRunningTime()
        setMovieDescription()
        setTicketCount(DEFAULT_TICKET_COUNT)
    }

    private fun setMoviePoster() {
        findViewById<ImageView>(R.id.imageBookingPoster).setImageResource(movieModel.poster)
    }

    private fun setMovieTitle() {
        findViewById<TextView>(R.id.textBookingTitle).text = movieModel.title
    }

    private fun setMovieScreeningDate() {
        findViewById<TextView>(R.id.textBookingScreeningDate).text =
            getString(R.string.screening_date).format(
                movieModel.screeningStartDate.formatDotDate(),
                movieModel.screeningEndDate.formatDotDate(),
            )
    }

    private fun setMovieRunningTime() {
        findViewById<TextView>(R.id.textBookingRunningTime).text =
            getString(R.string.running_time).format(movieModel.runningTime)
    }

    private fun setMovieDescription() {
        findViewById<TextView>(R.id.textBookingDescription).text = movieModel.description
    }

    private fun clickMinus() {
        findViewById<Button>(R.id.buttonBookingMinus).setOnClickListener {
            presenter.subTicket()
        }
    }

    private fun clickPlus() {
        findViewById<Button>(R.id.buttonBookingPlus).setOnClickListener {
            presenter.addTicket()
        }
    }

    private fun clickBookingComplete() {
        findViewById<Button>(R.id.buttonBookingChooseSeat).setOnClickListener {
            bookMovie()
        }
    }

    private fun bookMovie() {
        val dateTime = LocalDateTime.of(
            dateSpinnerAdapter.getItem(dateSpinner.selectedItemPosition),
            timeSpinnerAdapter.getItem(timeSpinner.selectedItemPosition),
        )
        val reservation = presenter.reserveMovie(dateTime)
        startActivity(ChoiceSeatActivity.getIntent(this, reservation))
    }

    private fun initAdapters() {
        dateSpinner.adapter = dateSpinnerAdapter
        timeSpinner.adapter = timeSpinnerAdapter
    }

    private fun initDateTimes() {
        dateSpinnerAdapter.initItems(presenter.getScreeningDate())
        timeSpinnerAdapter.initItems(presenter.getScreeningTime(0))
    }

    private fun initDateSpinnerSelectedListener() {
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                convertTimeItems(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    override fun convertTimeItems(position: Int) {
        val times: List<LocalTime> = presenter.getScreeningTime(position)
        timeSpinnerAdapter.initItems(times)
    }

    private fun initTimeSpinnerSelectedListener() {
        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) = Unit

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"
        private const val DATE_POSITION = "DATE_POSITION"
        private const val TIME_POSITION = "TIME_POSITION"
        private const val DEFAULT_TICKET_COUNT = 1
        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}
