package woowacourse.movie.view.home.booking

import AdapterItemSelectedListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.MovieStore
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.booking.PeopleCount
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.ext.getSerializableOrNull
import woowacourse.movie.view.ext.showToastFromResource
import woowacourse.movie.view.ext.toDrawableResourceId
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.home.seat.SeatActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {
    private lateinit var presenter: BookingContract.Presenter
    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)

        intent.getSerializableOrNull(KEY_SCREENING, ScreeningInfo::class.java)
            ?.let {
                presenter = BookingPresenter(this, MovieStore(), PeopleCount(), it)
                initView()
            } ?: run {
            showToastFromResource(R.string.error_missing_movie_info)
            finish()
        }
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.loadMovieDetail()

        initButtonListener()
    }

    override fun showMovieDetail(
        movie: Movie,
        screeningTimes: List<LocalDateTime>,
    ) {
        with(movie) {
            initTitleView(title)
            initPosterView(posterResource)

            initRunningTimeView(runningTime)
            presenter.loadScreeningTime(
                binding.spDate.selectedItem as LocalDate,
                LocalDateTime.now(),
            )
        }
    }

    override fun showPeopleCount(count: Int) {
        binding.tvPeopleCount.text = count.toString()
    }

    override fun showScreeningPeriod(
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        binding.tvScreeningPeriod.text =
            getString(R.string.text_date_period).format(
                StringFormatter.dotDateFormat(startDate),
                StringFormatter.dotDateFormat(endDate),
            )
    }

    override fun showScreeningDate(screeningBookingDates: List<LocalDate>) {
        with(binding.spDate) {
            adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    screeningBookingDates,
                )

            onItemSelectedListener =
                AdapterItemSelectedListener { pos ->
                    presenter.loadScreeningTime(screeningBookingDates[pos], LocalDateTime.now())
                }
        }
    }

    override fun showScreeningTime(screeningBookingTimes: List<LocalTime>) {
        with(binding.spTime) {
            adapter =
                ArrayAdapter(
                    this@BookingActivity,
                    android.R.layout.simple_spinner_item,
                    screeningBookingTimes,
                )
        }
    }

    override fun guideNoBookingTime() {
        showToastFromResource(R.string.text_no_booking_time)
    }

    override fun moveToBookingComplete(booking: Booking) {
        val intent = SeatActivity.newIntent(this, booking)
        startActivity(intent)
    }

    private fun initTitleView(title: String) {
        binding.tvTitle.text = title
    }

    private fun initPosterView(imgName: String) {
        val moviePosterView = findViewById<ImageView>(R.id.img_movie_poster)
        moviePosterView.setImageResource(imgName.toDrawableResourceId(this@BookingActivity))
    }

    private fun initRunningTimeView(runningTime: Int) {
        val movieRunningTimeView = findViewById<TextView>(R.id.tv_running_time)
        movieRunningTimeView.text =
            getString(R.string.text_running_time_ㅡminute_unit).format(runningTime)
    }

    private fun initButtonListener() {
        with(binding) {
            btnIncrease.setOnClickListener { presenter.increasePeopleCount(MAX_SEAT) }
            btnDecrease.setOnClickListener { presenter.decreasePeopleCount() }

            btnBookingComplete.setOnClickListener {
                presenter.loadBooking(
                    title = tvTitle.text.toString(),
                    bookingDate = spDate.selectedItem.toString(),
                    bookingTime = spTime.selectedItem.toString(),
                    peopleCount = tvPeopleCount.text.toString(),
                )
            }
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_PEOPLE_COUNT, binding.tvPeopleCount.text.toString().toInt())
        outState.putInt(KEY_SELECTED_TIME_POSITION, binding.spTime.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        with(savedInstanceState) {
            presenter.restorePeopleCount(getInt(KEY_PEOPLE_COUNT))
            val savedTimePosition = getInt(KEY_SELECTED_TIME_POSITION)
            binding.spTime.setSelection(savedTimePosition)
        }
    }

    companion object {
        const val KEY_SCREENING = "MOVIE_SCREENING"

        private const val MAX_SEAT = 20

        private const val KEY_SELECTED_TIME_POSITION = "SELECTED_TIME_POSITION"
        private const val KEY_PEOPLE_COUNT = "SAVED_PEOPLE_COUNT"

        fun newIntent(
            context: Context,
            screeningInfo: ScreeningInfo,
        ): Intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(KEY_SCREENING, screeningInfo)
            }
    }
}
