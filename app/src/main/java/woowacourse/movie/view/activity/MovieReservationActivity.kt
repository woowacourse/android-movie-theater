package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.contract.MovieReservationContract
import woowacourse.movie.data.LocalFormattedDate
import woowacourse.movie.data.LocalFormattedTime
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.error.ActivityError.finishWithError
import woowacourse.movie.error.ViewError
import woowacourse.movie.presenter.MovieReservationPresenter
import woowacourse.movie.system.BundleStateContainer
import woowacourse.movie.system.StateContainer
import woowacourse.movie.system.getSerializableCompat
import woowacourse.movie.view.widget.DateSpinner
import woowacourse.movie.view.widget.MovieController
import woowacourse.movie.view.widget.MovieView
import woowacourse.movie.view.widget.SaveStateSpinner
import woowacourse.movie.view.widget.TimeSpinner
import java.time.LocalDateTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override val presenter: MovieReservationContract.Presenter = MovieReservationPresenter(this)

    private val counterText: TextView by lazy {
        findViewById(R.id.movie_reservation_people_count)
    }

    private val dateSpinner: DateSpinner by lazy {
        DateSpinner(
            SaveStateSpinner(
                DATE_SPINNER_SAVE_STATE_KEY,
                findViewById(R.id.movie_reservation_date_spinner),
            )
        )
    }

    private val timeSpinner: TimeSpinner by lazy {
        TimeSpinner(
            SaveStateSpinner(
                TIME_SPINNER_SAVE_STATE_KEY,
                findViewById(R.id.movie_reservation_time_spinner),
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_reservation)
        makeBackButton()

        initMovieReservationView(savedInstanceState)
        presenter.load(savedInstanceState?.let { BundleStateContainer(it) })
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initMovieReservationView(savedInstanceState: Bundle?) {
        val movie =
            intent.extras?.getSerializableCompat<MovieViewData>(MovieViewData.MOVIE_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(MovieViewData.MOVIE_EXTRA_NAME))

        presenter.initActivity(movie)
        makeCounterListener()
        makeSpinners(savedInstanceState, movie)
        makeReservationButtonClickListener(movie)
    }

    override fun setMovieData(movie: MovieViewData) {
        MovieController.bind(
            movie = movie,
            MovieView(
                poster = findViewById(R.id.movie_reservation_poster),
                title = findViewById(R.id.movie_reservation_title),
                date = findViewById(R.id.movie_reservation_date),
                runningTime = findViewById(R.id.movie_reservation_running_time),
                description = findViewById(R.id.movie_reservation_description)
            )
        )
    }

    override fun setCounterText(count: Int) {
        counterText.text = count.toString()
    }

    override fun setTimeSpinner(times: List<LocalFormattedTime>) {
        timeSpinner.setTimes(times)
    }

    override fun saveTimeSpinner(outState: StateContainer) {
        timeSpinner.save((outState as BundleStateContainer).bundle)
    }

    override fun startReservationResultActivity(
        reservationDetail: ReservationDetailViewData,
        movie: MovieViewData
    ) {
        SeatSelectionActivity.from(this, movie, reservationDetail).run {
            startActivity(this)
        }
    }

    private fun makeCounterListener() {
        findViewById<Button>(R.id.movie_reservation_people_count_plus).setOnClickListener {
            presenter.addPeopleCount(COUNT_FACTOR)
        }
        findViewById<Button>(R.id.movie_reservation_people_count_minus).setOnClickListener {
            presenter.minusPeopleCount(COUNT_FACTOR)
        }
    }

    private fun makeSpinners(savedInstanceState: Bundle?, movie: MovieViewData) {
        dateSpinner.make(
            savedInstanceState = savedInstanceState, movie = movie
        )
        dateSpinner.spinner.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    presenter.selectDate(dateSpinner.dates[position].date)
                    timeSpinner.make(savedInstanceState)
                }
            }
    }

    private fun makeReservationButtonClickListener(
        movie: MovieViewData
    ) {
        findViewById<Button>(R.id.movie_reservation_button).setOnClickListener {
            val date = makeReservationDate(dateSpinner, timeSpinner)
            presenter.reserveMovie(date, movie)
        }
    }

    private fun makeReservationDate(
        dateSpinner: DateSpinner,
        timeSpinner: TimeSpinner,
    ): LocalDateTime {
        return LocalDateTime.of(
            (dateSpinner.spinner.spinner.selectedItem as LocalFormattedDate).date,
            (timeSpinner.spinner.spinner.selectedItem as LocalFormattedTime).time
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        presenter.save(BundleStateContainer(outState))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val COUNT_FACTOR = 1
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
        fun from(context: Context, movie: MovieViewData): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
            }
        }
    }
}
