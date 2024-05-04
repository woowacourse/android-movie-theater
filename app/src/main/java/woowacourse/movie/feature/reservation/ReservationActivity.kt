package woowacourse.movie.feature.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.feature.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.feature.seatselection.SeatSelectionActivity
import woowacourse.movie.feature.theater.TheaterSelectionFragment.Companion.THEATER_ID
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.HeadCount.Companion.DEFAULT_HEAD_COUNT
import woowacourse.movie.utils.MovieUtils.convertPeriodFormat
import woowacourse.movie.utils.MovieUtils.makeToast

class ReservationActivity : AppCompatActivity(), ReservationContract.View {
    private val binding: ActivityReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_reservation,
        )
    }
    private lateinit var presenter: ReservationPresenter
    private var movieId: Int = 0
    private var theaterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.reservation = this

        movieId = receiveMovieId()
        theaterId = receiveTheaterId()
        val savedHeadCount = bringSavedHeadCount(savedInstanceState)

        initPresenter(savedHeadCount)
        with(presenter) {
            loadMovie()
            loadScreeningPeriod()
        }
        updateScreeningTimes(DEFAULT_TIME_ID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(HEAD_COUNT, binding.tvReservationHeadCount.text.toString().toInt())
            putInt(SCREENING_PERIOD, binding.spinnerReservationScreeningDate.selectedItemPosition)
            putInt(SCREENING_TIME, binding.spinnerReservationScreeningTime.selectedItemPosition)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.restoreHeadCount()
        val selectedTimeId = savedInstanceState.getInt(SCREENING_TIME, 0)
        updateScreeningTimes(selectedTimeId)
    }

    override fun showMovieInformation(movie: Movie) {
        with(binding) {
            ivReservationPoster.setImageResource(movie.posterId)
            tvReservationTitle.text = movie.title
            tvReservationScreeningDate.text = convertPeriodFormat(movie.screeningPeriod)
            tvReservationRunningTime.text = movie.runningTime
            tvReservationSummary.text = movie.summary
        }
    }

    override fun showScreeningPeriod(movie: Movie) {
        binding.spinnerReservationScreeningDate.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                movie.screeningPeriod,
            )
    }

    override fun showScreeningTimes(
        screeningTimes: ScreeningTimes,
        selectedDate: String,
    ) {
        binding.spinnerReservationScreeningTime.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningTimes.loadScheduleByDateType(selectedDate),
            )
    }

    override fun changeHeadCount(count: Int) {
        binding.tvReservationHeadCount.text = count.toString()
    }

    override fun showResultToast() = makeToast(this, getString(R.string.invalid_number_of_tickets))

    override fun showErrorToast() = makeToast(this, getString(R.string.all_error))

    override fun navigateToSeatSelection(
        dateTime: ScreeningDateTime,
        count: HeadCount,
    ) {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        intent.apply {
            putExtra(MOVIE_ID, movieId)
            putExtra(THEATER_ID, theaterId)
            putExtra(SCREENING_DATE_TIME, dateTime)
            putExtra(HEAD_COUNT, count)
        }
        startActivity(intent)
    }

    private fun initPresenter(savedHeadCount: Int) {
        presenter =
            ReservationPresenter(
                view = this,
                ScreeningDao(),
                TheaterDao(),
                movieId,
                theaterId,
                savedHeadCount,
            )
    }

    private fun receiveMovieId() = intent.getIntExtra(MOVIE_ID, DEFAULT_MOVIE_ID)

    private fun receiveTheaterId() = intent.getIntExtra(THEATER_ID, DEFAULT_THEATER_ID)

    private fun bringSavedHeadCount(savedInstanceState: Bundle?) = savedInstanceState?.getInt(HEAD_COUNT) ?: DEFAULT_HEAD_COUNT

    private fun updateScreeningTimes(selectedTimeId: Int? = null) {
        binding.spinnerReservationScreeningDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = binding.spinnerReservationScreeningDate.selectedItem.toString()
                    presenter.loadScreeningTimes(selectedDate)
                    selectedTimeId?.let {
                        if (selectedTimeId < binding.spinnerReservationScreeningTime.count) {
                            binding.spinnerReservationScreeningTime.setSelection(it)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d(SELECTED_DATE_TAG, NOTHING_SELECTED_MESSAGE)
                }
            }
    }

    fun initializeMinusButton() = presenter.decreaseHeadCount()

    fun initializePlusButton() = presenter.increaseHeadCount()

    fun initializeReservationButton() {
        val date = binding.spinnerReservationScreeningDate.selectedItem.toString()
        val time = binding.spinnerReservationScreeningTime.selectedItem.toString()
        val dateTime = ScreeningDateTime(date, time)
        presenter.sendTicketToSeatSelection(dateTime)
    }

    companion object {
        const val DEFAULT_MOVIE_ID = 0
        const val DEFAULT_THEATER_ID = 0
        const val TICKET = "ticket"
        const val HEAD_COUNT = "headCount"
        const val SCREENING_DATE_TIME = "screeningDateTime"
        const val SELECTED_DATE_TAG = "notSelectedDate"
        const val NOTHING_SELECTED_MESSAGE = "nothingSelected"
        private const val DEFAULT_TIME_ID = 0
        private const val SCREENING_TIME = "screeningTime"
        private const val SCREENING_PERIOD = "screeningPeriod"
    }
}
