package woowacourse.movie.booking.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.booking.detail.adapter.ScreeningDateSpinnerAdapter
import woowacourse.movie.booking.detail.adapter.ScreeningTimeSpinnerAdapter
import woowacourse.movie.booking.detail.listener.ScreeningDateSelectedListener
import woowacourse.movie.booking.detail.listener.ScreeningTimeSelectedListener
import woowacourse.movie.databinding.ActivityBookingDetailBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.seat.SeatSelectionActivity
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel
import woowacourse.movie.ui.model.TicketUiModel
import java.time.LocalDate
import java.time.LocalTime

class BookingDetailActivity : AppCompatActivity(), BookingDetailContract.View {
    private val presenter = BookingDetailPresenter(this)
    private lateinit var binding: ActivityBookingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_detail)
        binding.detail = presenter
        setUpUi()

        presenter.initializeData(requireMovieOrFinish(), requireTheaterOrFinish())

        if (savedInstanceState != null) {
            val headCount = savedInstanceState.getInt(KEY_HEAD_COUNT)
            val screeningDate = savedInstanceState.getString(KEY_SCREENING_DATE)
            val screeningTime = savedInstanceState.getString(KEY_SCREENING_TIME)
            presenter.restoreTicketData(headCount, screeningDate, screeningTime)
        } else {
            presenter.createDefaultTicket()
        }
        presenter.setUpTicket()

        initReserveConfirm()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requireMovieOrFinish(): MovieUiModel {
        return IntentCompat.getParcelableExtra(intent, KEY_MOVIE_DATA, MovieUiModel::class.java)
    }

    private fun requireTheaterOrFinish(): TheaterUiModel {
        return IntentCompat.getParcelableExtra(intent, KEY_THEATER_DATA, TheaterUiModel::class.java)
    }

    private fun initReserveConfirm() {
        val btnReserveConfirm = binding.btnSelectionConfirm
        btnReserveConfirm.setOnClickListener {
            presenter.confirmReservation()
        }
    }

    override fun showMovieInfo(movie: MovieUiModel) {
        binding.movie = movie
    }

    override fun showHeadCount(headCount: Int) {
        binding.tvPeopleCount.text = headCount.toString()
    }

    override fun showScreeningDates(
        dates: List<LocalDate>,
        selected: LocalDate,
    ) {
        val dateSpinner = binding.spinnerScreeningDate
        dateSpinner.adapter = ScreeningDateSpinnerAdapter(this, dates)

        val position = dates.indexOf(selected)
        if (position != INVALID_POSITION_VALUE) {
            dateSpinner.setSelection(position)
        }

        dateSpinner.onItemSelectedListener =
            ScreeningDateSelectedListener(
                onDateSelected = { date ->
                    presenter.selectDate(date)
                },
            )
    }

    override fun showScreeningTimes(
        times: List<LocalTime>,
        selected: LocalTime,
    ) {
        val timeSpinner = binding.spinnerScreeningTime
        timeSpinner.adapter = ScreeningTimeSpinnerAdapter(this, times)

        val position = times.indexOf(selected)
        if (position != INVALID_POSITION_VALUE) {
            timeSpinner.setSelection(position)
        }

        timeSpinner.onItemSelectedListener =
            ScreeningTimeSelectedListener(
                onTimeSelected = { time ->
                    presenter.selectTime(time)
                },
            )
    }

    override fun startSeatSelectionActivity(ticket: TicketUiModel) {
        val intent = SeatSelectionActivity.newIntent(this, ticket)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_HEAD_COUNT, binding.tvPeopleCount.text.toString().toInt())
        outState.putString(KEY_SCREENING_DATE, binding.spinnerScreeningDate.selectedItem.toString())
        outState.putString(KEY_SCREENING_TIME, binding.spinnerScreeningTime.selectedItem.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val INVALID_POSITION_VALUE = -1
        private const val KEY_MOVIE_DATA = "MOVIE_DATA"
        private const val KEY_THEATER_DATA = "THEATER_DATA"
        private const val KEY_HEAD_COUNT = "HEAD_COUNT"
        private const val KEY_SCREENING_DATE = "SCREENING_DATE"
        private const val KEY_SCREENING_TIME = "SCREENING_TIME"

        fun newIntent(
            context: Context,
            movie: MovieUiModel,
            theater: TheaterUiModel,
        ): Intent =
            Intent(context, BookingDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE_DATA, movie)
                putExtra(KEY_THEATER_DATA, theater)
            }
    }
}
