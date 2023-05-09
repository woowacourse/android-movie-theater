package woowacourse.movie.view.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.model.MovieModel
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TheaterModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.utils.getParcelable
import woowacourse.movie.utils.movieDetailFormat
import woowacourse.movie.view.seat.SeatPickerActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private val peopleCountView: TextView by lazy { findViewById(R.id.detail_people_count) }
    private val dateSpinner: Spinner by lazy { findViewById(R.id.detail_date_spinner) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.detail_time_spinner) }
    private lateinit var timeSpinnerAdapter: ArrayAdapter<LocalTime>

    private lateinit var theater: TheaterModel

    override lateinit var presenter: MovieDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MovieDetailPresenter(this)

        initIntentData()

        setMinusButton()
        setPlusButton()

        loadSavedData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(DATE_SPINNER_POSITION_INSTANCE_KEY, dateSpinner.selectedItemPosition)
        outState.putInt(TIME_SPINNER_POSITION_INSTANCE_KEY, timeSpinner.selectedItemPosition)
        outState.putInt(PEOPLE_COUNT_VALUE_INSTANCE_KEY, peopleCountView.text.toString().toInt())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initIntentData() {
        intent.getParcelable<MovieModel>(MOVIE_EXTRA_KEY)?.let {
            setMovieInfo(it)
            setBookingButton(it)
            presenter.setScreeningDates(it)
        }

        intent.getParcelable<TheaterModel>(THEATER_EXTRA_KEY)?.let {
            theater = it
            presenter.setScreeningTimes(theater.screeningTimes)
        }
    }

    private fun setMovieInfo(movie: MovieModel) {
        val posterView = findViewById<ImageView>(R.id.detail_poster)
        val titleView = findViewById<TextView>(R.id.detail_title)
        val dateView = findViewById<TextView>(R.id.detail_date)
        val runningTimeView = findViewById<TextView>(R.id.detail_running_time)
        val descriptionView = findViewById<TextView>(R.id.detail_description)

        posterView.setImageResource(movie.poster)
        titleView.text = movie.title
        dateView.text = movie.getScreenDate()
        runningTimeView.text = movie.getRunningTime()
        descriptionView.text = movie.description
    }

    private fun MovieModel.getScreenDate(): String =
        getString(R.string.screen_date, startDate.movieDetailFormat(), endDate.movieDetailFormat())

    private fun MovieModel.getRunningTime(): String = getString(R.string.running_time, runningTime)

    private fun setMinusButton() {
        val minusButton = findViewById<Button>(R.id.detail_minus_button)
        minusButton.setOnClickListener {
            presenter.minusPeopleCount()
        }
    }

    private fun setPlusButton() {
        val plusButton = findViewById<Button>(R.id.detail_plus_button)
        plusButton.setOnClickListener {
            presenter.plusPeopleCount()
        }
    }

    private fun setBookingButton(movie: MovieModel) {
        val bookingButton = findViewById<Button>(R.id.detail_booking_button)

        bookingButton.setOnClickListener {
            moveToSeatPickerActivity(movie)
        }
    }

    private fun moveToSeatPickerActivity(movie: MovieModel) {
        val ticket = MovieTicketModel(
            theater.name,
            movie.title,
            TicketTimeModel(
                LocalDateTime.of(
                    dateSpinner.selectedItem as LocalDate,
                    timeSpinner.selectedItem as LocalTime
                )
            ),
            PeopleCountModel(peopleCountView.text.toString().toInt()),
            seats = emptySet(),
            PriceModel(0)
        )

        val intent = SeatPickerActivity.createIntent(this, ticket)
        startActivity(intent)
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        val datePosition = savedInstanceState?.getInt(DATE_SPINNER_POSITION_INSTANCE_KEY) ?: 0
        val timePosition = savedInstanceState?.getInt(TIME_SPINNER_POSITION_INSTANCE_KEY) ?: 0
        val count = savedInstanceState?.getInt(PEOPLE_COUNT_VALUE_INSTANCE_KEY) ?: 1
        dateSpinner.setSelection(datePosition)
        timeSpinner.setSelection(timePosition)
        presenter.setPeopleCount(count)
    }

    override fun setPeopleCount(count: Int) {
        peopleCountView.text = count.toString()
    }

    override fun setDateSpinner(dates: List<LocalDate>) {
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dates
        )
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateSpinner.adapter = dateSpinnerAdapter
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                timeSpinner.setSelection(0)
                timeSpinnerAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun setTimeSpinner(times: List<LocalTime>) {
        timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            times
        )
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeSpinnerAdapter
    }

    companion object {
        private const val DATE_SPINNER_POSITION_INSTANCE_KEY = "date_position"
        private const val MOVIE_EXTRA_KEY = "movie"
        private const val THEATER_EXTRA_KEY = "theater"
        private const val PEOPLE_COUNT_VALUE_INSTANCE_KEY = "count"
        private const val TIME_SPINNER_POSITION_INSTANCE_KEY = "time_position"

        fun createIntent(context: Context, movie: MovieModel, theater: TheaterModel): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA_KEY, movie)
            intent.putExtra(THEATER_EXTRA_KEY, theater)
            return intent
        }
    }
}
