package woowacourse.movie.view.home.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.feed.Feed.Movie
import woowacourse.movie.view.home.model.ScreeningInfo
import woowacourse.movie.view.home.seat.SeatActivity
import woowacourse.movie.view.util.StringFormatter
import woowacourse.movie.view.util.getSerializableCompat
import woowacourse.movie.view.util.showToast
import woowacourse.movie.view.util.toDrawableResourceId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View, BookingEventHandler {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var presenter: BookingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookingBinding.inflate(layoutInflater)
        binding.handler = this
        setContentView(binding.root)
        initView()

        val screeningInfo: ScreeningInfo? = intent.extras?.getSerializableCompat(KEY_SCREENING)
        if (screeningInfo == null) {
            showToast(getString(R.string.text_error))
            finish()
            return
        }

        presenter = BookingPresenter(this, screeningInfo)
        presenter.loadBooking(LocalDateTime.now())
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showMovieDetail(movie: Movie) {
        val poster = movie.posterResource.toDrawableResourceId(this@BookingActivity)
        val formattedRunningTime = getString(R.string.text_running_time_minute_unit).format(movie.runningTime)
        val formattedScreeningPeriod =
            getString(R.string.text_date_period).format(
                StringFormatter.dotDateFormat(movie.startDate),
                StringFormatter.dotDateFormat(movie.endDate),
            )

        binding.tvTitle.text = movie.title
        binding.imgMoviePoster.setImageResource(poster)
        binding.tvRunningTime.text = formattedRunningTime
        binding.tvScreeningPeriod.text = formattedScreeningPeriod
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_BOOKING, presenter.booking)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val booking: Booking? = savedInstanceState.getSerializableCompat(KEY_BOOKING)
        if (booking == null) {
            showToast(getString(R.string.text_error))
            finish()
            return
        }
        presenter.restoreBooking(booking)
    }

    override fun showAdmissionCount(count: Int) {
        binding.tvAdmissionCount.text = count.toString()
    }

    override fun showScreeningDates(bookableDates: List<LocalDate>) {
        with(binding) {
            spDate.adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    bookableDates,
                )

            spDate.onItemSelectedListener =
                AdapterItemSelectedListener { position ->
                    presenter.selectDate(bookableDates[position])
                }
        }
    }

    override fun showScreeningTimes(
        bookableTimes: List<LocalTime>,
        savedTime: LocalTime,
    ) {
        with(binding) {
            spTime.adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    bookableTimes,
                )

            spTime.onItemSelectedListener =
                AdapterItemSelectedListener { position ->
                    presenter.selectTime(bookableTimes[position])
                }

            spTime.setSelection(bookableTimes.indexOf(savedTime))
        }
    }

    override fun notifyNoAvailableTime() {
        Toast.makeText(this, R.string.text_no_booking_time, Toast.LENGTH_LONG).show()
    }

    override fun moveToBookingComplete(booking: Booking) {
        val intent = SeatActivity.newIntent(this, booking)
        startActivity(intent)
    }

    override fun onIncreaseAdmissionCount() {
        presenter.increaseAdmissionCount(MAX_SEAT)
    }

    override fun onDecreaseAdmissionCount() {
        presenter.decreaseAdmissionCount()
    }

    override fun onBookingComplete() {
        presenter.completeBooking()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val KEY_SCREENING = "screening"
        private const val KEY_BOOKING = "booking"

        private const val MAX_SEAT = 20

        fun newIntent(
            context: Context,
            screeningInfo: ScreeningInfo,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(KEY_SCREENING, screeningInfo)
            }
    }
}
