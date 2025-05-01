package woowacourse.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.SeatSelectionActivity.Companion.KEY_TICKET
import woowacourse.movie.booking.detail.BookingDetailContract
import woowacourse.movie.booking.detail.BookingDetailPresenter
import woowacourse.movie.booking.detail.TicketUiModel
import woowacourse.movie.booking.detail.adapter.ScreeningDateSpinnerAdapter
import woowacourse.movie.booking.detail.adapter.ScreeningTimeSpinnerAdapter
import woowacourse.movie.booking.detail.listener.ScreeningDateSelectedListener
import woowacourse.movie.booking.detail.listener.ScreeningTimeSelectedListener
import woowacourse.movie.databinding.ActivityBookingDetailBinding
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.movie.MovieUiModel
import woowacourse.movie.movie.TheaterUiModel
import java.time.LocalDate
import java.time.LocalTime

class BookingDetailActivity : AppCompatActivity(), BookingDetailContract.View {
    private lateinit var presenter: BookingDetailContract.Presenter
    private lateinit var binding: ActivityBookingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_detail)
        binding.detail = this
        setUpUi()

        val movieData = requireMovieOrFinish()
        val theaterData = requireTheaterOrFinish()
        presenter = BookingDetailPresenter(this, movieData, theaterData)

        if (savedInstanceState != null) {
            val headCount = savedInstanceState.getInt(KEY_HEAD_COUNT)
            val screeningDate = savedInstanceState.getString(KEY_SCREENING_DATE)
            val screeningTime = savedInstanceState.getString(KEY_SCREENING_TIME)
            presenter.restoreTicketData(headCount, screeningDate, screeningTime)
        } else {
            presenter.createDefaultTicket()
        }

        presenter.initializeData()
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
            ?: run {
                Log.e(TAG, "인텐트에 영화 예매 정보(KEY_MOVIE_DATA)가 없습니다")
                showToastErrorAndFinish(getString(R.string.booking_toast_message))
                throw IllegalStateException("Movie 데이터가 없어서 Activity를 종료했습니다")
            }
    }

    private fun requireTheaterOrFinish(): TheaterUiModel {
        return IntentCompat.getParcelableExtra(intent, KEY_THEATER_DATA, TheaterUiModel::class.java)
            ?: run {
                Log.e(TAG, "인텐트에 극장 정보(KEY_THEATER_DATA)가 없습니다")
                showToastErrorAndFinish(getString(R.string.booking_toast_message))
                throw IllegalStateException("Theater 데이터가 없어서 Activity를 종료했습니다")
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showMovieInfo(movie: MovieUiModel) {
        binding.movie = movie
    }

    fun decreaseHeadCount() {
        presenter.decreaseHeadCount()
        showHeadCount()
    }

    fun increaseHeadCount() {
        presenter.increaseHeadCount()
        showHeadCount()
    }

    override fun showHeadCount() {
        binding.tvPeopleCount.text = presenter.getCurrentTicketUiModel().headCount.toString()
    }

    override fun showScreeningDates(
        dates: List<LocalDate>,
        selected: LocalDate,
    ) {
        val dateSpinner = binding.spinnerScreeningDate
        dateSpinner.adapter = ScreeningDateSpinnerAdapter(this, dates)

        val position = dates.indexOf(selected)
        if (position != -1) {
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
        if (position != -1) {
            timeSpinner.setSelection(position)
        }

        timeSpinner.onItemSelectedListener =
            ScreeningTimeSelectedListener(
                onTimeSelected = { time ->
                    presenter.selectTime(time)
                },
            )
    }

    private fun initReserveConfirm() {
        val btnReserveConfirm = binding.btnSelectionConfirm
        btnReserveConfirm.setOnClickListener {
            presenter.confirmReservation()
        }
    }

    override fun startSeatSelectionActivity(ticket: TicketUiModel) {
        val intent =
            Intent(this, SeatSelectionActivity::class.java).apply {
                putExtra(KEY_TICKET, ticket)
            }
        startActivity(intent)
    }

    override fun showToastErrorAndFinish(message: String) {
        Log.d(TAG, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val ticketUiModel = presenter.getCurrentTicketUiModel()

        outState.putInt(KEY_HEAD_COUNT, ticketUiModel.headCount)
        outState.putString(KEY_SCREENING_DATE, ticketUiModel.selectedDateText)
        outState.putString(KEY_SCREENING_TIME, ticketUiModel.selectedTimeText)
    }

    companion object {
        private const val TAG = "BookingDetailActivity"
        const val KEY_MOVIE_DATA = "movieData"
        const val KEY_THEATER_DATA = "theaterData"
        private const val KEY_HEAD_COUNT = "HEAD_COUNT"
        private const val KEY_SCREENING_DATE = "SCREENING_DATE"
        private const val KEY_SCREENING_TIME = "SCREENING_TIME"
    }
}
