package woowacourse.movie.view.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationDetailBinding
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.presenter.reservation.ReservationDetailContract
import woowacourse.movie.presenter.reservation.ReservationDetailPresenter
import woowacourse.movie.utils.MovieUtils.convertPeriodFormat
import woowacourse.movie.utils.MovieUtils.makeToast
import woowacourse.movie.view.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.view.theater.TheaterSelectionFragment.Companion.THEATER_ID

class ReservationDetailActivity : AppCompatActivity(), ReservationDetailContract.View {
    private val binding: ActivityReservationDetailBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_reservation_detail,
        )
    }
    private val presenter: ReservationDetailPresenter = ReservationDetailPresenter(this, ScreeningDao(), TheaterDao())
    private var movieId: Int = 0
    private var theaterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.reservationDetail = this

        movieId = intent.getIntExtra(MOVIE_ID, DEFAULT_ID)
        theaterId = intent.getIntExtra(THEATER_ID, DEFAULT_ID)

        presenter.checkIdValidation(movieId, theaterId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(HEAD_COUNT, presenter.headCount.count)
            putInt(SCREENING_PERIOD, binding.spinnerReservationDetailScreeningDate.selectedItemPosition)
            putInt(SCREENING_TIME, binding.spinnerReservationDetailScreeningTime.selectedItemPosition)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let { bundle ->
            runCatching {
                bundle.getInt(HEAD_COUNT)
            }.onSuccess { headCount ->
                presenter.headCount.restore(headCount)
                binding.textViewReservationDetailNumberOfTickets.text = presenter.headCount.count.toString()
            }.onFailure {
                showErrorMessage()
                finish()
            }

            val selectedTimeId = bundle.getInt(SCREENING_TIME, 0)
            updateScreeningTimes(selectedTimeId)
        }
    }

    override fun showMovieInformation(movie: Movie) {
        with(binding) {
            imageViewReservationDetailPoster.setImageResource(movie.posterId)
            textViewReservationDetailTitle.text = movie.title
            textViewReservationScreeningDate.text =
                convertPeriodFormat(
                    movie.screeningPeriod,
                    binding.root.context,
                )
            textViewReservationRunningTime.text = movie.runningTime
            textViewReservationSummary.text = movie.summary
        }
    }

    override fun showScreeningPeriod(movie: Movie) {
        binding.spinnerReservationDetailScreeningDate.adapter =
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
        binding.spinnerReservationDetailScreeningTime.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                screeningTimes.loadScheduleByDateType(selectedDate),
            )
    }

    override fun changeHeadCount(count: Int) {
        binding.textViewReservationDetailNumberOfTickets.text = count.toString()
    }

    override fun showResultMessage() = makeToast(this, getString(R.string.invalid_number_of_tickets))

    override fun showErrorMessage() = makeToast(this, getString(R.string.all_error))

    override fun showIdErrorMessage() = makeToast(this, getString(R.string.load_error))

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

    override fun updateScreeningTimes(selectedTimeId: Int?) {
        binding.spinnerReservationDetailScreeningDate.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = binding.spinnerReservationDetailScreeningDate.selectedItem.toString()
                    presenter.loadScreeningTimes(theaterId, selectedDate)
                    selectedTimeId?.let {
                        if (selectedTimeId < binding.spinnerReservationDetailScreeningTime.count) {
                            binding.spinnerReservationDetailScreeningTime.setSelection(it)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d(SELECTED_DATE_TAG, NOTHING_SELECTED_MESSAGE)
                }
            }
    }

    fun initializeMinusButton() = presenter.decreaseHeadCount(presenter.headCount.count)

    fun initializePlusButton() = presenter.increaseHeadCount(presenter.headCount.count)

    fun initializeReservationButton() {
        val date = binding.spinnerReservationDetailScreeningDate.selectedItem.toString()
        val time = binding.spinnerReservationDetailScreeningTime.selectedItem.toString()
        val dateTime = ScreeningDateTime(date, time)
        presenter.initializeReservationButton(dateTime)
    }

    companion object {
        const val DEFAULT_ID = -1
        const val DEFAULT_TICKET_ID = -1L
        const val TICKET = "ticket"
        const val RESERVATION_TICKET_ID = "ticketId"
        const val HEAD_COUNT = "headCount"
        const val SCREENING_DATE_TIME = "screeningDateTime"
        const val SELECTED_DATE_TAG = "notSelectedDate"
        const val NOTHING_SELECTED_MESSAGE = "nothingSelected"
        private const val SCREENING_TIME = "screeningTime"
        private const val SCREENING_PERIOD = "screeningPeriod"
    }
}
