package woowacourse.movie.presentation.booking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.presentation.seats.SeatsActivity
import woowacourse.movie.ui.DataBindingBaseActivity
import woowacourse.movie.ui.constant.IntentKeys
import woowacourse.movie.ui.util.PosterMapper
import woowacourse.movie.ui.util.intentSerializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

class BookingActivity :
    DataBindingBaseActivity<ActivityBookingBinding>(),
    BookingContract.View {
    override val layoutRes: Int
        get() = R.layout.activity_booking

    override lateinit var binding: ActivityBookingBinding

    private lateinit var presenter: BookingContract.Presenter
    private lateinit var screeningInfo: ScreeningInfo
    private var dateItemPosition: Int = DEFAULT_POSITION
    private var timeItemPosition: Int = DEFAULT_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!fetchMovieFromIntent()) return
        setupScreen()
        presenter = BookingPresenter(this, screeningInfo)
        presenter.onViewCreated()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(HEADCOUNT_KEY, binding.textviewHeadcount.text.toString())
        outState.putInt(DATE_POSITION_KEY, dateItemPosition)
        outState.putInt(TIME_POSITION_KEY, timeItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val count = savedInstanceState.getString(HEADCOUNT_KEY)
        dateItemPosition = savedInstanceState.getInt(DATE_POSITION_KEY)
        timeItemPosition = savedInstanceState.getInt(TIME_POSITION_KEY)
        presenter.onConfigurationChanged(
            count?.toIntOrNull(),
            binding.spinnerDate.getItemAtPosition(dateItemPosition) as LocalDate?,
            binding.spinnerTime.getItemAtPosition(timeItemPosition) as LocalTime?,
        )
    }

    override fun initBooking() {
        bindHeadCountButtonListeners()
        bindSelectButtonListener()
    }

    override fun showMovie(movie: Movie) {
        binding.imageviewPoster.setImageResource(PosterMapper.convertTitleToResId(movie.title))
        binding.textviewTitle.text = movie.title
        binding.textviewScreeningdate.text =
            getString(R.string.date_text, movie.startScreeningDate, movie.endScreeningDate)
        binding.textviewRunningtime.text = getString(R.string.runningTime_text, movie.runningTime.toString())
    }

    override fun showBookableDates(dates: List<LocalDate>) {
        val dateSpinner = binding.spinnerDate
        dateSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                dates,
            )
        dateSpinner.setSelection(dateItemPosition)
        dateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = dateSpinner.getItemAtPosition(position) as LocalDate
                    presenter.onDateSelected(selectedDate)
                    dateItemPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun showBookableTimes(times: List<LocalTime>) {
        val timeSpinner = binding.spinnerTime
        timeSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                times,
            )
        timeSpinner.setSelection(timeItemPosition)
        timeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedTime = timeSpinner.getItemAtPosition(position) as LocalTime
                    presenter.onTimeSelected(selectedTime)
                    timeItemPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun updateHeadCount(count: Int) {
        binding.textviewHeadcount.text = String.format(Locale.getDefault(), INTEGER_FORMAT, count)
    }

    override fun navigateToSeats(ticket: MovieTicket) {
        val intent =
            Intent(this, SeatsActivity::class.java).apply {
                putExtra(IntentKeys.TICKET, ticket)
            }
        startActivity(intent)
    }

    private fun fetchMovieFromIntent(): Boolean {
        val data = intent.intentSerializable(IntentKeys.SCREENING_INFO, ScreeningInfo::class.java)
        if (data == null) {
            Toast.makeText(this, MOVIE_INTENT_ERROR, Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        screeningInfo = data
        return true
    }

    private fun bindHeadCountButtonListeners() {
        binding.buttonIncrease.setOnClickListener {
            presenter.onIncreaseHeadCount()
        }
        binding.buttonDecrease.setOnClickListener {
            presenter.onDecreaseHeadCount()
        }
    }

    private fun bindSelectButtonListener() {
        binding.buttonSelect.setOnClickListener {
            presenter.onConfirmClicked()
        }
    }

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val HEADCOUNT_KEY = "HeadCount"
        private const val DATE_POSITION_KEY = "Date"
        private const val TIME_POSITION_KEY = "Time"
        private const val INTEGER_FORMAT = "%d"
        private const val MOVIE_INTENT_ERROR = "[ERROR] 영화 정보에 대한 키 값이 올바르지 않습니다."
    }
}
