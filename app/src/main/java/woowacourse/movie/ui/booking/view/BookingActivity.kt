package woowacourse.movie.ui.booking.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.ui.booking.contract.BookingContract
import woowacourse.movie.ui.booking.presenter.BookingPresenter
import woowacourse.movie.ui.seat.view.BookingSeatActivity
import woowacourse.movie.utils.StringFormatter
import woowacourse.movie.utils.bundleSerializable
import woowacourse.movie.utils.intentSerializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity :
    AppCompatActivity(),
    BookingContract.View {
    private lateinit var binding: ActivityBookingBinding

    private val bookingPresenter = BookingPresenter(this)

    private val dateSpinner: Spinner by lazy { binding.spDate }
    private val timeSpinner: Spinner by lazy { binding.spTime }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

        applyWindowInsets()

        initializeFromIntent()
        bookingPresenter.updateViews()
        setButtonClickListeners()
    }

    private fun initializeFromIntent() {
        bookingPresenter.loadState(
            intent.intentSerializable(EXTRA_THEATER, Theater::class.java) ?: Theater(),
            Headcount(),
            intent.intentSerializable(EXTRA_MOVIE, Movie::class.java)
                ?: DUMMY_MOVIES.first(),
            0,
            0
        )
    }

    override fun setMovieInfoViews(movie: Movie) {
        binding.movie = movie
        binding.stringFormatter = StringFormatter
        bookingPresenter.refreshHeadcountDisplay()
    }

    override fun updateHeadcountDisplay(headcount: Headcount) {
        binding.headcount = headcount
    }

    override fun setDateSpinner(
        spinnerItems: List<LocalDate>,
        position: Int,
    ) {
        with(dateSpinner) {
            adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    spinnerItems,
                )
            if (spinnerItems.isNotEmpty()) {
                setSelection(position)
            }

            onItemSelectedListener =
                AdapterItemSelectedListener { pos ->
                    bookingPresenter.loadSelectedDate(selectedItem as LocalDate, pos)
                    bookingPresenter.setupTimeSpinner()
                }
        }
        bookingPresenter.setupTimeSpinner()
    }

    override fun setTimeSpinner(
        spinnerItems: List<LocalTime>,
        position: Int,
    ) {
        with(timeSpinner) {
            adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    spinnerItems,
                )
            if (spinnerItems.isNotEmpty()) {
                setSelection(position)
            }
            onItemSelectedListener =
                AdapterItemSelectedListener { pos ->
                    bookingPresenter.loadSelectedTime(pos)
                    bookingPresenter.loadSelectedDateTime(
                        LocalDateTime.of(
                            binding.spDate.selectedItem as LocalDate,
                            selectedItem as LocalTime
                        )
                    )
                }
        }
    }

    override fun startBookingSeatActivity(
        movieTitle: String,
        dateTime: LocalDateTime,
        headcount: Headcount,
        theater: Theater,
    ) {
        startActivity(BookingSeatActivity.newIntent(this, movieTitle, dateTime, headcount, theater))
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
        outState.putSerializable(KEY_HEADCOUNT, binding.headcount)
        outState.putInt(KEY_SELECTED_DATE_POSITION, dateSpinner.selectedItemPosition)
        outState.putInt(KEY_SELECTED_TIME_POSITION, timeSpinner.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val headcount =
            savedInstanceState.bundleSerializable(
                KEY_HEADCOUNT,
                Headcount::class.java,
            ) as Headcount
        val selectedDatePosition: Int = savedInstanceState.getInt(KEY_SELECTED_DATE_POSITION)
        val selectedTimePosition: Int = savedInstanceState.getInt(KEY_SELECTED_TIME_POSITION)

        bookingPresenter.loadState(
            intent.intentSerializable(EXTRA_THEATER, Theater::class.java) ?: Theater(),
            headcount,
            intent.intentSerializable(EXTRA_MOVIE, Movie::class.java)
                ?: DUMMY_MOVIES.first(),
            selectedDatePosition,
            selectedTimePosition
        )
        bookingPresenter.updateViews()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setButtonClickListeners() {
        setIncreaseButtonClickListener()
        setDecreaseButtonClickListener()
        setBookingCompleteButtonClickListener()
    }

    private fun setIncreaseButtonClickListener() {
        binding.btnIncrease.setOnClickListener {
            bookingPresenter.increaseHeadcount()
            bookingPresenter.refreshHeadcountDisplay()
        }
    }

    private fun setDecreaseButtonClickListener() {
        binding.btnDecrease.setOnClickListener {
            bookingPresenter.decreaseHeadcount()
            bookingPresenter.refreshHeadcountDisplay()
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
            movie: Movie,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(EXTRA_THEATER, theater)
                putExtra(EXTRA_MOVIE, movie)
            }

        private const val KEY_SELECTED_DATE_POSITION = "KEY_DATE_POSITION"
        private const val KEY_SELECTED_TIME_POSITION = "KEY_TIME_POSITION"
        private const val KEY_HEADCOUNT = "KEY_PEOPLE_COUNT"

        private const val EXTRA_THEATER = "EXTRA_THEATER"
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
