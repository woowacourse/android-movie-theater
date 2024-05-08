package woowacourse.movie.feature.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.feature.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.feature.seatselection.SeatSelectionActivity
import woowacourse.movie.feature.theater.TheaterSelectionFragment.Companion.THEATER_ID
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.theater.Theater.Companion.DEFAULT_THEATER_ID
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.HeadCount.Companion.DEFAULT_HEAD_COUNT
import woowacourse.movie.utils.MovieUtils.makeToast
import java.time.LocalDate
import java.time.LocalTime

class ReservationActivity : AppCompatActivity(), ReservationContract.View {
    private val binding: ActivityReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_reservation,
        )
    }
    private lateinit var presenter: ReservationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.activity = this

        val savedHeadCount = bringSavedHeadCount(savedInstanceState)
        initPresenter(savedHeadCount)
        presenter.loadMovieInformation()
        setOnScreeningDateSelectedListener()
        setOnScreeningTimeSelectedListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(HEAD_COUNT, binding.tvReservationHeadCount.text.toString().toInt())
        }
    }

    override fun showMovieInformation(movie: Movie) {
        with(binding) {
            this.movie = movie
            screeningStartDate = movie.screeningPeriod.first()
            screeningEndDate = movie.screeningPeriod.last()
        }
    }

    override fun showScreeningDates(screeningDates: List<LocalDate>) {
        binding.spinnerReservationScreeningDate.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningDates,
            )
    }

    override fun showScreeningTimes(screeningTimes: List<LocalTime>) {
        binding.spinnerReservationScreeningTime.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningTimes,
            )
    }

    override fun changeHeadCount(count: Int) {
        binding.headCount = count
    }

    override fun showResultToast() = makeToast(this, getString(R.string.invalid_number_of_tickets))

    override fun navigateToSeatSelection(
        dateTime: ScreeningDateTime,
        movieId: Int,
        theaterId: Int,
        count: HeadCount,
    ) {
        val intent =
            Intent(this, SeatSelectionActivity::class.java)
                .putExtra(MOVIE_ID, movieId)
                .putExtra(THEATER_ID, theaterId)
                .putExtra(SCREENING_DATE_TIME, dateTime)
                .putExtra(HEAD_COUNT, count)
        startActivity(intent)
    }

    override fun showScreeningDate(selectedDateId: Long) {
        binding.spinnerReservationScreeningDate.setSelection(selectedDateId.toInt())
    }

    override fun showScreeningTime(selectedTimeId: Long) {
        binding.spinnerReservationScreeningTime.setSelection(selectedTimeId.toInt())
    }

    override fun showDateTime(dateTime: ScreeningDateTime) {
        binding.dateTime = dateTime
    }

    override fun showErrorSnackBar() {
        val snackBar =
            Snackbar.make(
                binding.root,
                getString(R.string.all_error),
                Snackbar.LENGTH_INDEFINITE,
            )
        snackBar.setAction(R.string.all_confirm) {
            snackBar.dismiss()
            finish()
        }
        snackBar.show()
    }

    private fun bringSavedHeadCount(savedInstanceState: Bundle?) = savedInstanceState?.getInt(HEAD_COUNT) ?: DEFAULT_HEAD_COUNT

    private fun initPresenter(savedHeadCount: Int) {
        val movieId = receiveMovieId()
        val theaterId = receiveTheaterId()
        presenter =
            ReservationPresenter(
                view = this,
                ScreeningDao(),
                TheaterDao(),
                movieId,
                theaterId,
                savedHeadCount,
            )
        binding.presenter = presenter
    }

    private fun receiveMovieId() = intent.getIntExtra(MOVIE_ID, DEFAULT_MOVIE_ID)

    private fun receiveTheaterId() = intent.getIntExtra(THEATER_ID, DEFAULT_THEATER_ID)

    private fun setOnScreeningDateSelectedListener() {
        binding.spinnerReservationScreeningDate.setOnItemSelectedListener { selectDateId -> presenter.selectScreeningDate(selectDateId) }
    }

    private fun setOnScreeningTimeSelectedListener() {
        binding.spinnerReservationScreeningTime.setOnItemSelectedListener { selectTimeId -> presenter.selectScreeningTime(selectTimeId) }
    }

    private fun Spinner.setOnItemSelectedListener(onSelectItem: OnSelectedSpinnerItem) {
        onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    onSelectItem(id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d(SPINNER_TAG, NOTHING_SELECTED_MESSAGE)
                }
            }
    }

    companion object {
        const val TICKET = "ticket"
        const val HEAD_COUNT = "headCount"
        const val SCREENING_DATE_TIME = "screeningDateTime"
        private const val SPINNER_TAG = "spinner"
        private const val NOTHING_SELECTED_MESSAGE = "nothingSelected"
    }
}

typealias OnSelectedSpinnerItem = (itemId: Long) -> Unit
