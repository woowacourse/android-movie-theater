package woowacourse.movie.activity.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.activity.seatselection.SeatSelectionActivity
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable
import woowacourse.movie.view.widget.DateSpinnerManager
import woowacourse.movie.view.widget.SaveStateSpinner
import woowacourse.movie.view.widget.TimeSpinnerManager
import java.time.LocalDateTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override lateinit var presenter: MovieReservationContract.Presenter
    private val countText: TextView by lazy {
        findViewById(R.id.movie_reservation_people_count)
    }

    private val dateSpinner: Spinner by lazy {
        findViewById(R.id.movie_reservation_date_spinner)
    }
    private val timeSpinner: Spinner by lazy {
        findViewById(R.id.movie_reservation_time_spinner)
    }

    private val saveStateDateSpinner: SaveStateSpinner by lazy {
        SaveStateSpinner(DATE_SPINNER_SAVE_STATE_KEY, dateSpinner)
    }
    private val dateSpinnerManager: DateSpinnerManager by lazy {
        DateSpinnerManager(findViewById(R.id.movie_reservation_date_spinner))
    }

    private val timeSpinnerManager: TimeSpinnerManager by lazy {
        TimeSpinnerManager(findViewById(R.id.movie_reservation_time_spinner))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_reservation)
        presenter = MovieReservationPresenter(
            this, saveStateDateSpinner, SaveStateSpinner(TIME_SPINNER_SAVE_STATE_KEY, timeSpinner)
        )
        initMovieReservationView(savedInstanceState)
    }

    private fun initMovieReservationView(savedInstanceState: Bundle?) {
        makeBackButton()
        initButton()
        val movie = getIntentMovieData() ?: return finishWithError(
            ViewError.MissingExtras(
                MovieViewData.MOVIE_EXTRA_NAME
            )
        )
        presenter.initCount(savedInstanceState)
        presenter.initDateSpinner(movie, savedInstanceState)
        presenter.renderMovie(movie)
        makeReservationButtonClickListener(movie)
    }

    private fun getIntentMovieData(): MovieViewData? =
        intent.extras?.getSerializable<MovieViewData>(MovieViewData.MOVIE_EXTRA_NAME)

    private fun initButton() {
        findViewById<Button>(R.id.movie_reservation_people_count_minus).setOnClickListener {
            presenter.minusCount()
        }
        findViewById<Button>(R.id.movie_reservation_people_count_plus).setOnClickListener {
            presenter.plusCount()
        }
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun makeReservationButtonClickListener(
        movie: MovieViewData
    ) {
        findViewById<Button>(R.id.movie_reservation_button).setOnClickListener {
            val reservationDetail = presenter.getReservationDetailView(
                LocalDateTime.of(
                    (dateSpinner.selectedItem as LocalFormattedDate).date,
                    (timeSpinner.selectedItem as LocalFormattedTime).time
                )
            )
            SeatSelectionActivity.from(this, movie, reservationDetail).run {
                startActivity(this)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        presenter.saveCount(outState)
        presenter.saveDate(outState)
        presenter.saveTime(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initCount(count: Int) {
        countText.text = count.toString()
    }

    override fun initDateSpinner(dateIndex: Int, timeIndex: Int, dates: List<LocalFormattedDate>) {
        dateSpinnerManager.initSpinner(
            dates, timeSpinnerManager, dateIndex, timeIndex
        )
    }

    override fun setCount(count: Int) {
        countText.text = count.toString()
    }

    override fun renderMovie(
        image: Int,
        title: String,
        date: String,
        runningTime: String,
        description: String
    ) {
        findViewById<ImageView>(R.id.movie_reservation_poster).setImageResource(image)
        findViewById<TextView>(R.id.movie_reservation_title).text = title
        findViewById<TextView>(R.id.movie_reservation_date).text = date
        findViewById<TextView>(R.id.movie_reservation_running_time).text = runningTime
        findViewById<TextView>(R.id.movie_reservation_description).text = description
    }

    companion object {
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
        fun from(context: Context, movie: MovieViewData): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
            }
        }
    }
}
