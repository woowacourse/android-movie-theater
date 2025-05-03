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
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
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

    private val dateSpinner: Spinner by lazy { findViewById(R.id.sp_date) }
    private val timeSpinner: Spinner by lazy { findViewById(R.id.sp_time) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

        applyWindowInsets()

        bookingPresenter.loadTheater()
        bookingPresenter.updateViews()
        setButtonClickListeners()
    }

    override fun getTheater(): Theater? = intent.intentSerializable(EXTRA_THEATER, Theater::class.java)

    override fun getSelectedDateTime(): LocalDateTime =
        LocalDateTime.of(
            dateSpinner.selectedItem as LocalDate,
            timeSpinner.selectedItem as LocalTime,
        )

    override fun getSelectedDate(): LocalDate = dateSpinner.selectedItem as LocalDate

    override fun getSelectedTimePosition(): Int = timeSpinner.selectedItemPosition

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
        outState.putSerializable(KEY_PEOPLE_COUNT, bookingPresenter.headcount)
        outState.putInt(KEY_SELECTED_DATE_POSITION, dateSpinner.selectedItemPosition)
        outState.putInt(KEY_SELECTED_TIME_POSITION, timeSpinner.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val headcount =
            savedInstanceState.bundleSerializable(
                KEY_PEOPLE_COUNT,
                Headcount::class.java,
            ) as Headcount
        val selectedDatePosition: Int = savedInstanceState.getInt(KEY_SELECTED_DATE_POSITION)
        val selectedTimePosition: Int = savedInstanceState.getInt(KEY_SELECTED_TIME_POSITION)

        bookingPresenter.setHeadcount(headcount)
        bookingPresenter.setSelectedDatePosition(selectedDatePosition)
        bookingPresenter.setSelectedTimePosition(selectedTimePosition)
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
        val increaseBtn: Button = findViewById(R.id.btn_increase)
        increaseBtn.setOnClickListener {
            bookingPresenter.increaseHeadcount()
            bookingPresenter.refreshHeadcountDisplay()
        }
    }

    private fun setDecreaseButtonClickListener() {
        val decreaseBtn: Button = findViewById(R.id.btn_decrease)
        decreaseBtn.setOnClickListener {
            bookingPresenter.decreaseHeadcount()
            bookingPresenter.refreshHeadcountDisplay()
        }
    }

    private fun setBookingCompleteButtonClickListener() {
        val bookingCompleteBtn: Button = findViewById(R.id.btn_booking_complete)
        bookingCompleteBtn.setOnClickListener {
            bookingPresenter.completeBooking()
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            theater: Theater,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(EXTRA_THEATER, theater)
            }

        private const val KEY_SELECTED_DATE_POSITION = "SELECTED_DATE_POSITION"
        private const val KEY_SELECTED_TIME_POSITION = "SELECTED_TIME_POSITION"
        private const val KEY_PEOPLE_COUNT = "SAVED_PEOPLE_COUNT"

        private const val EXTRA_THEATER = "EXTRA_THEATER"
    }
}
