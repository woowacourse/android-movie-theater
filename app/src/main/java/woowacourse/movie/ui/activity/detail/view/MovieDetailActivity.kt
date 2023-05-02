package woowacourse.movie.ui.activity.detail.view

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
import woowacourse.movie.ui.activity.SeatPickerActivity
import woowacourse.movie.ui.activity.detail.MovieDetailContract
import woowacourse.movie.ui.activity.detail.presenter.MovieDetailPresenter
import woowacourse.movie.ui.model.MovieModel
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.mapToMovie
import woowacourse.movie.ui.utils.getParcelable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    override lateinit var presenter: MovieDetailContract.Presenter
    private val dateSpinner: Spinner by lazy { findViewById(R.id.detail_date_spinner) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.detail_time_spinner) }
    private lateinit var timeSpinnerAdapter: ArrayAdapter<LocalTime>
    private val peopleCountView by lazy { findViewById<TextView>(R.id.detail_people_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        presenter = MovieDetailPresenter(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getParcelable<MovieModel>(MOVIE_EXTRA_KEY)?.let {
            setMovieInfo(it)
            setDateSpinner(it)
            setBookingButton(it)
        }
        presenter.initTimes(dateSpinner.selectedItem as LocalDate)
        setPeopleCountController()

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

    override fun setPeopleCount(count: Int) {
        peopleCountView.text = count.toString()
    }

    override fun initTimeSpinner(times: List<LocalTime>) {
        timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            times
        )
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeSpinnerAdapter
    }

    override fun updateTimeSpinner() {
        timeSpinnerAdapter.notifyDataSetChanged()
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
        getString(R.string.screen_date, startDate.format(), endDate.format())

    private fun LocalDate.format(): String =
        format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))

    private fun MovieModel.getRunningTime(): String = getString(R.string.running_time, runningTime)

    private fun setDateSpinner(movie: MovieModel) {
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            movie.mapToMovie().getDatesBetweenTwoDates()
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
                presenter.updateTimes(dateSpinner.selectedItem as LocalDate)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setPeopleCountController() {
        setMinusButton()
        setPlusButton()
    }

    private fun setMinusButton() {
        val minusButton = findViewById<Button>(R.id.detail_minus_button)
        minusButton.setOnClickListener {
            presenter.decreasePeopleCount()
        }
    }

    private fun setPlusButton() {
        val plusButton = findViewById<Button>(R.id.detail_plus_button)
        plusButton.setOnClickListener {
            presenter.increasePeopleCount()
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

    companion object {
        private const val DATE_SPINNER_POSITION_INSTANCE_KEY = "date_position"
        private const val MOVIE_EXTRA_KEY = "movie"
        private const val PEOPLE_COUNT_VALUE_INSTANCE_KEY = "count"
        private const val TIME_SPINNER_POSITION_INSTANCE_KEY = "time_position"

        fun createIntent(context: Context, movie: MovieModel): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA_KEY, movie)
            return intent
        }
    }
}
