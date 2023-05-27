package woowacourse.movie.activity.moviereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.activity.seatselection.SeatSelectionActivity
import woowacourse.movie.databinding.ActivityMovieReservationBinding
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.getSerializable
import woowacourse.movie.view.widget.DateSpinnerManager
import woowacourse.movie.view.widget.TimeSpinnerManager

class MovieReservationActivity : AppCompatActivity(), MovieReservationContract.View {
    lateinit var presenter: MovieReservationContract.Presenter
    private val binding: ActivityMovieReservationBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_movie_reservation)
    }
    private val dateSpinnerManager: DateSpinnerManager by lazy {
        DateSpinnerManager(binding.movieReservationDateSpinner)
    }

    private val timeSpinnerManager: TimeSpinnerManager by lazy {
        TimeSpinnerManager(binding.movieReservationTimeSpinner)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theaterMovie = getIntentMovieData()
            ?: return finishWithError(TheaterMovieViewData.THEATER_MOVIE_EXTRA_NAME)
        presenter = MovieReservationPresenter(
            this,
            theaterMovie,
            savedInstanceState?.getInt(COUNT_SAVE_STATE_KEY) ?: 1,
            savedInstanceState?.getInt(DATE_SPINNER_SAVE_STATE_KEY) ?: 0,
            savedInstanceState?.getInt(TIME_SPINNER_SAVE_STATE_KEY) ?: 0,
        )
        initMovieReservationView(theaterMovie)
    }

    private fun initMovieReservationView(theaterMovie: TheaterMovieViewData) {
        makeBackButton()
        initButton()
        presenter.setUpMovie(theaterMovie)
        presenter.loadCountData()
        presenter.loadDateTimeData()
        initMovieReservationButton(theaterMovie)
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

    private fun initMovieReservationButton(theaterMovie: TheaterMovieViewData) {
        binding.movieReservationButton.setOnClickListener {
            presenter.navigateToSeatSelection(
                (binding.movieReservationDateSpinner.selectedItem as LocalFormattedDate).date,
                (binding.movieReservationTimeSpinner.selectedItem as LocalFormattedTime).time,
                theaterMovie.theaterName,
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_SAVE_STATE_KEY, presenter.getCount())
        outState.putInt(
            DATE_SPINNER_SAVE_STATE_KEY,
            binding.movieReservationDateSpinner.selectedItemPosition,
        )
        outState.putInt(
            TIME_SPINNER_SAVE_STATE_KEY,
            binding.movieReservationTimeSpinner.selectedItemPosition,
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setUpMovie(theaterMovie: TheaterMovieViewData) {
        binding.theaterMovie = theaterMovie
    }

    override fun initCount(count: Int) {
        binding.movieReservationPeopleCount.text = count.toString()
    }

    override fun initDateSpinner(dateIndex: Int, dates: List<LocalFormattedDate>) {
        dateSpinnerManager.initSpinner(
            dates,
            dateIndex,
        )
    }

    override fun initTimeSpinner(timeIndex: Int, times: List<LocalFormattedTime>) {
        timeSpinnerManager.initSpinner(times, timeIndex)
    }

    override fun updateCount(count: Int) {
        binding.movieReservationPeopleCount.text = count.toString()
    }

    override fun navigateToSeatSelection(
        theaterMovie: TheaterMovieViewData,
        reservationDetailViewData: ReservationDetailViewData,
    ) {
        SeatSelectionActivity.from(this, theaterMovie.movie, reservationDetailViewData).run {
            startActivity(this)
        }
    }

    companion object {
        private const val COUNT_SAVE_STATE_KEY = "count"
        private const val DATE_SPINNER_SAVE_STATE_KEY = "date_spinner"
        private const val TIME_SPINNER_SAVE_STATE_KEY = "time_spinner"
        fun from(context: Context, theaterMovie: TheaterMovieViewData): Intent {
            return Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(TheaterMovieViewData.THEATER_MOVIE_EXTRA_NAME, theaterMovie)
            }
        }
    }
}
