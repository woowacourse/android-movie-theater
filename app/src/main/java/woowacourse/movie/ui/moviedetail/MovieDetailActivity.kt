package woowacourse.movie.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.ui.seat.SeatSelectionActivity
import woowacourse.movie.utils.failLoadingData
import woowacourse.movie.utils.getParcelableCompat
import woowacourse.movie.utils.getSerializableExtraCompat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    override val presenter = MovieDetailPresenter(this)

    private val minusButton: Button by lazy {
        findViewById(R.id.detail_minus_button)
    }
    private val plusButton: Button by lazy {
        findViewById(R.id.detail_plus_button)
    }
    private val peopleCountView: TextView by lazy {
        findViewById(R.id.detail_people_count)
    }
    private val dateSpinner: Spinner by lazy {
        findViewById(R.id.detail_date_spinner)
    }
    private val timeSpinner: Spinner by lazy {
        findViewById(R.id.detail_time_spinner)
    }

    private lateinit var timeSpinnerAdapter: ArrayAdapter<LocalTime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie: MovieListModel.MovieModel =
            intent.getParcelableCompat(KEY_MOVIE) ?: return failLoadingData()

        setMovieInfo(movie)
        initSpinner(movie)
        initPeopleCountController()
        initBookingButton(movie)
        loadSavedData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt(KEY_DATE_POSITION, dateSpinner.selectedItemPosition)
        outState.putInt(KEY_TIME_POSITION, timeSpinner.selectedItemPosition)
        outState.putSerializable(KEY_PEOPLE_COUNT, presenter.peopleCountModel)
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

    private fun setMovieInfo(movie: MovieListModel.MovieModel) {
        findViewById<ImageView>(R.id.detail_poster).setImageResource(movie.poster)
        findViewById<TextView>(R.id.detail_title).text = movie.title
        findViewById<TextView>(R.id.detail_date).text =
            getString(R.string.screening_date, movie.startDate.format(), movie.endDate.format())
        findViewById<TextView>(R.id.detail_running_time).text =
            getString(R.string.running_time, movie.runningTime)
        findViewById<TextView>(R.id.detail_description).text = movie.description
    }

    private fun LocalDate.format(): String =
        format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))

    private fun initSpinner(movie: MovieListModel.MovieModel) {
        setDateSpinner(movie)
        setTimeSpinner()
    }

    private fun setDateSpinner(movie: MovieListModel.MovieModel) {
        val dateSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            presenter.getDatesBetweenTwoDates(movie),
        )
        dateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dateSpinner.adapter = dateSpinnerAdapter
        dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                presenter.updateTimesByDate(dateSpinner.selectedItem as LocalDate)
                timeSpinner.setSelection(0)
                timeSpinnerAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setTimeSpinner() {
        presenter.updateTimesByDate(dateSpinner.selectedItem as LocalDate)
        timeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            presenter.times,
        )
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = timeSpinnerAdapter
    }

    private fun initPeopleCountController() {
        setMinusButton()
        setPlusButton()
        setPeopleCountView(presenter.peopleCountModel.count)
    }

    private fun setMinusButton() {
        minusButton.setOnClickListener {
            presenter.minusCount()
        }
    }

    private fun setPlusButton() {
        plusButton.setOnClickListener {
            presenter.addCount()
        }
    }

    override fun setPeopleCountView(count: Int) {
        peopleCountView.text = "$count"
    }

    private fun initBookingButton(movie: MovieListModel.MovieModel) {
        val bookingButton = findViewById<Button>(R.id.detail_booking_button)

        bookingButton.setOnClickListener {
            moveToSeatSelectionActivity(movie)
        }
    }

    private fun moveToSeatSelectionActivity(movie: MovieListModel.MovieModel) {
        val intent = Intent(this, SeatSelectionActivity::class.java).apply {
            putExtra(KEY_TITLE, movie.title)
            putExtra(
                KEY_TIME,
                LocalDateTime.of(
                    dateSpinner.selectedItem as LocalDate,
                    timeSpinner.selectedItem as LocalTime,
                ),
            )
            putExtra(KEY_PEOPLE_COUNT, presenter.peopleCountModel)
        }
        startActivity(intent)
        finish()
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        dateSpinner.setSelection(savedInstanceState?.getInt(KEY_DATE_POSITION) ?: 0)
        timeSpinner.setSelection(savedInstanceState?.getInt(KEY_TIME_POSITION) ?: 0)
        presenter.updatePeopleCount(
            savedInstanceState?.getSerializableExtraCompat<PeopleCountModel>(KEY_PEOPLE_COUNT)?.count
                ?: 1,
        )
    }

    companion object {
        const val KEY_MOVIE = "movie"
        private const val KEY_DATE_POSITION = "date_position"
        private const val KEY_TIME_POSITION = "time_position"
        const val KEY_TITLE = "title"
        const val KEY_TIME = "time"
        const val KEY_PEOPLE_COUNT = "count"

        fun getIntent(movie: MovieListModel.MovieModel, context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE, movie)
            }
        }
    }
}
