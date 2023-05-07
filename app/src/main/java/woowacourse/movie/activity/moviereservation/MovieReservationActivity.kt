package woowacourse.movie.activity.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.activity.seatselection.SeatSelectionActivity
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable
import woowacourse.movie.view.widget.DateSpinnerManager
import woowacourse.movie.view.widget.SaveStateSpinner
import woowacourse.movie.view.widget.TimeSpinnerManager
import java.time.LocalDateTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override lateinit var presenter: MovieReservationContract.Presenter
    private val binding: ActivityMovieReservationBinding by lazy {
        ActivityMovieReservationBinding.inflate(layoutInflater)
    }

    private val saveStateDateSpinner: SaveStateSpinner by lazy {
        SaveStateSpinner(DATE_SPINNER_SAVE_STATE_KEY, binding.movieReservationDateSpinner)
    }
    private val dateSpinnerManager: DateSpinnerManager by lazy {
        DateSpinnerManager(binding.movieReservationDateSpinner)
    }

    private val timeSpinnerManager: TimeSpinnerManager by lazy {
        TimeSpinnerManager(binding.movieReservationTimeSpinner)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = MovieReservationPresenter(
            this,
            saveStateDateSpinner,
            SaveStateSpinner(TIME_SPINNER_SAVE_STATE_KEY, binding.movieReservationTimeSpinner)
        )
        initMovieReservationView(savedInstanceState)
    }

    private fun initMovieReservationView(savedInstanceState: Bundle?) {
        makeBackButton()
        initButton()
        val movie = getIntentMovieData() ?: return finishWithError(
            ViewError.MissingExtras(
                TheaterMovieViewData.THEATER_MOVIE_EXTRA_NAME
            )
        )
        presenter.initCount(savedInstanceState)
        presenter.initDateSpinner(movie, savedInstanceState)
        presenter.initTimeSpinner(movie, savedInstanceState)
        presenter.renderMovie(movie)
        makeReservationButtonClickListener(movie)
    }

    private fun getIntentMovieData(): TheaterMovieViewData? =
        intent.extras?.getSerializable<TheaterMovieViewData>(TheaterMovieViewData.THEATER_MOVIE_EXTRA_NAME)

    private fun initButton() {
        binding.movieReservationPeopleCountMinus.setOnClickListener {
            presenter.minusCount()
        }
        binding.movieReservationPeopleCountPlus.setOnClickListener {
            presenter.plusCount()
        }
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun makeReservationButtonClickListener(
        movie: TheaterMovieViewData
    ) {
        binding.movieReservationButton.setOnClickListener {
            val reservationDetail = presenter.getReservationDetailView(
                LocalDateTime.of(
                    (binding.movieReservationDateSpinner.selectedItem as LocalFormattedDate).date,
                    (binding.movieReservationTimeSpinner.selectedItem as LocalFormattedTime).time
                ),
                movie.theaterName
            )
            SeatSelectionActivity.from(this, movie.movie, reservationDetail).run {
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
        binding.movieReservationPeopleCount.text = count.toString()
    }

    override fun initDateSpinner(dateIndex: Int, dates: List<LocalFormattedDate>) {
        dateSpinnerManager.initSpinner(
            dates, dateIndex
        )
    }

    override fun initTimeSpinner(timeIndex: Int, times: List<LocalFormattedTime>) {
        timeSpinnerManager.initSpinner(times, timeIndex)
    }

    override fun setCount(count: Int) {
        binding.movieReservationPeopleCount.text = count.toString()
    }

    override fun renderMovie(
        image: Int,
        title: String,
        date: String,
        runningTime: String,
        description: String
    ) {
        binding.movieReservationPoster.setImageResource(image)
        binding.movieReservationTitle.text = title
        binding.movieReservationDate.text = date
        binding.movieReservationRunningTime.text = runningTime
        binding.movieReservationDescription.text = description
    }

    companion object {
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
        fun from(context: Context, theaterMovie: TheaterMovieViewData): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(TheaterMovieViewData.THEATER_MOVIE_EXTRA_NAME, theaterMovie)
            }
        }
    }
}
