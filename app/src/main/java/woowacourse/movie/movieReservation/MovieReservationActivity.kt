package woowacourse.movie.movieReservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.common.error.ActivityError.finishWithError
import woowacourse.movie.common.error.ViewError
import woowacourse.movie.common.model.LocalFormattedDate
import woowacourse.movie.common.model.LocalFormattedTime
import woowacourse.movie.common.model.MovieScheduleViewData
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.TheaterViewData
import woowacourse.movie.common.system.BundleStateContainer
import woowacourse.movie.common.system.StateContainer
import woowacourse.movie.common.system.getSerializableCompat
import woowacourse.movie.common.view.widget.MovieView
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.seatSelection.SeatSelectionActivity
import java.time.LocalDateTime

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    override lateinit var presenter: MovieReservationContract.Presenter
    private lateinit var binding: ActivityMovieReservationBinding

    private val dateSpinner: DateSpinner by lazy {
        DateSpinner(
            SaveStateSpinner(
                DATE_SPINNER_SAVE_STATE_KEY,
                binding.movieReservationDateSpinner,
            )
        )
    }

    private val timeSpinner: TimeSpinner by lazy {
        TimeSpinner(
            SaveStateSpinner(
                TIME_SPINNER_SAVE_STATE_KEY,
                binding.movieReservationTimeSpinner,
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)
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
        val movieSchedule =
            intent.extras?.getSerializableCompat<MovieScheduleViewData>(MovieScheduleViewData.MOVIE_SCHEDULE_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(MovieScheduleViewData.MOVIE_SCHEDULE_EXTRA_NAME))
        val theaterName =
            intent.extras?.getSerializableCompat<String>(TheaterViewData.THEATER_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(TheaterViewData.THEATER_EXTRA_NAME))

        presenter =
            MovieReservationPresenter(this, movieSchedule = movieSchedule, movieViewData = movie)
        makeCounterListener()
        makeSpinners(savedInstanceState, movie)
        makeReservationButtonClickListener(movie, theaterName)
    }

    override fun setMovieData(movie: MovieViewData) {
        MovieView(
            poster = binding.movieReservationPoster,
            title = binding.movieReservationTitle,
            date = binding.movieReservationDate,
            runningTime = binding.movieReservationRunningTime,
            description = binding.movieReservationDescription
        ).bind(movie)
    }

    override fun setCounterText(count: Int) {
        binding.movieReservationPeopleCount.text = count.toString()
    }

    override fun setTimeSpinner(times: List<LocalFormattedTime>) {
        timeSpinner.setTimes(times)
    }

    override fun saveTimeSpinner(outState: StateContainer) {
        timeSpinner.save((outState as BundleStateContainer).bundle)
    }

    override fun startReservationResultActivity(
        reservationDetail: ReservationDetailViewData,
        movie: MovieViewData,
        theaterName: String
    ) {
        SeatSelectionActivity.from(this, movie, reservationDetail, theaterName).run {
            startActivity(this)
        }
    }

    private fun makeCounterListener() {
        binding.movieReservationPeopleCountPlus.setOnClickListener {
            presenter.addPeopleCount(COUNT_FACTOR)
        }
        binding.movieReservationPeopleCountMinus.setOnClickListener {
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
                    presenter.selectDate()
                    timeSpinner.make(savedInstanceState)
                }
            }
    }

    private fun makeReservationButtonClickListener(
        movie: MovieViewData,
        theaterName: String
    ) {
        binding.movieReservationButton.setOnClickListener {
            val date = makeReservationDate(dateSpinner, timeSpinner)
            presenter.reserveMovie(date, movie, theaterName)
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
        fun from(
            context: Context,
            movie: MovieViewData,
            movieScheduleViewData: MovieScheduleViewData,
            theaterName: String
        ): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(MovieViewData.MOVIE_EXTRA_NAME, movie)
                putExtra(MovieScheduleViewData.MOVIE_SCHEDULE_EXTRA_NAME, movieScheduleViewData)
                putExtra(TheaterViewData.THEATER_EXTRA_NAME, theaterName)
            }
        }
    }
}
