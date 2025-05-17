package woowacourse.movie.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.booking.spinner.ScreeningDateSpinner
import woowacourse.movie.ui.booking.spinner.ScreeningTimeSpinner
import woowacourse.movie.ui.booking.spinner.listener.ScreeningDateListener
import woowacourse.movie.ui.booking.spinner.listener.ScreeningTimeListener
import woowacourse.movie.ui.seat.BookingSeatActivity
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDate
import java.time.LocalTime

class BookingActivity :
    AppCompatActivity(),
    BookingContract.View {
    private lateinit var binding: ActivityBookingBinding
    private val bookingPresenter by lazy { BookingPresenter(this) }
    private lateinit var screeningDateSpinner: ScreeningDateSpinner
    private lateinit var screeningTimeSpinner: ScreeningTimeSpinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)
        applyWindowInsets()

        bookingPresenter.loadBookingInfos(restoreTheater(), restoreMovieId())
        setButtonClickListeners()
    }

    override fun showMovie(movie: Movie) {
        binding.movie = movie
        binding.stringFormatter = StringFormatter
    }

    override fun displayScreeningDateSpinner(dates: List<LocalDate>) {
        screeningDateSpinner =
            ScreeningDateSpinner(
                binding.spDate,
                dates,
            )
        screeningDateSpinner.setOnItemSelectedListener(
            ScreeningDateListener(
                onSelectDate = { screeningDate ->
                    bookingPresenter.updateScreeningDate(screeningDate)
                },
            ),
        )
    }

    override fun displayScreeningTimeSpinner(times: List<LocalTime>) {
        screeningTimeSpinner =
            ScreeningTimeSpinner(
                binding.spTime,
            )
        screeningTimeSpinner.updateAdapter(times)
        screeningTimeSpinner.setOnItemSelectedListener(
            ScreeningTimeListener(
                onSelectTime = { screeningTime ->
                    bookingPresenter.updateScreeningTime(screeningTime)
                },
            ),
        )
    }

    override fun displayScreeningTimeSpinnerItems(times: List<LocalTime>) {
        screeningTimeSpinner.updateAdapter(times)
    }

    override fun showScreeningDate(position: Int) {
        binding.spDate.setSelection(position, false)
    }

    override fun showScreeningTime(position: Int) {
        binding.spTime.setSelection(position)
    }

    override fun showHeadCount(headcount: Headcount) {
        binding.headcount = headcount
    }

    override fun moveToSelectSeat(
        movieId: Long,
        movieSchedule: MovieSchedule,
        headcount: Headcount,
        theaterName: String,
    ) {
        startActivity(
            BookingSeatActivity.newIntent(
                this,
                movieId,
                movieSchedule,
                headcount,
                theaterName,
            ),
        )
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PEOPLE_COUNT, binding.headcount?.count ?: 1)
        outState.putInt(KEY_SELECTED_DATE_POSITION, binding.spDate.selectedItemPosition)
        outState.putInt(KEY_SELECTED_TIME_POSITION, binding.spTime.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val headcount = savedInstanceState.getInt(KEY_PEOPLE_COUNT)
        val selectedDatePosition: Int = savedInstanceState.getInt(KEY_SELECTED_DATE_POSITION)
        val selectedTimePosition: Int = savedInstanceState.getInt(KEY_SELECTED_TIME_POSITION)

        bookingPresenter.restoreBookingInfos(headcount, selectedDatePosition, selectedTimePosition)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun restoreTheater(): Theater? = intent.intentSerializable(EXTRA_THEATER, Theater::class.java)

    private fun restoreMovieId(): Long = intent.getLongExtra(EXTRA_MOVIE_ID, 0L)

    private fun setButtonClickListeners() {
        setIncreaseButtonClickListener()
        setDecreaseButtonClickListener()
        setBookingCompleteButtonClickListener()
    }

    private fun setIncreaseButtonClickListener() {
        binding.btnIncrease.setOnClickListener {
            bookingPresenter.increaseHeadcount()
        }
    }

    private fun setDecreaseButtonClickListener() {
        binding.btnDecrease.setOnClickListener {
            bookingPresenter.decreaseHeadcount()
        }
    }

    private fun setBookingCompleteButtonClickListener() {
        binding.btnBookingComplete.setOnClickListener {
            bookingPresenter.completeBooking()
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            theater: Theater,
            movieId: Long,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(EXTRA_THEATER, theater)
                putExtra(EXTRA_MOVIE_ID, movieId)
            }

        private const val KEY_SELECTED_DATE_POSITION = "SELECTED_DATE_POSITION"
        private const val KEY_SELECTED_TIME_POSITION = "SELECTED_TIME_POSITION"
        private const val KEY_PEOPLE_COUNT = "SAVED_PEOPLE_COUNT"

        private const val EXTRA_THEATER = "EXTRA_THEATER"
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    }
}
