package woowacourse.movie.moviebooking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieBookingBinding
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater
import woowacourse.movie.helper.BuildVersion
import woowacourse.movie.moviebookingseat.MovieBookingSeatActivity
import java.time.LocalDate
import java.time.LocalTime

class MovieBookingActivity : AppCompatActivity(), MovieBooking.View {
    private lateinit var binding: MovieBookingBinding
    private lateinit var presenter: MovieBookingPresenter
    private lateinit var movie: Movie
    private lateinit var theater: Theater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        applyWindowInserts()
        initIntentData()
        setUpPresenter()
        setupDatePicker()
        setupTimePicker()
        setupMemberCount()
        setupBookingCompleteButton()
    }

    override fun showMovieInfo() {
        binding.movie = movie
    }

    override fun updateMemberCount(count: Int) {
        binding.bookingMemberCount.text = count.toString()
    }

    override fun showBookingDate(dates: List<LocalDate>) {
        binding.bookingDatePicker.adapter = BookedDateSpinnerAdapter(dates)
    }

    override fun showBookingTimes(times: List<LocalTime>) {
        binding.bookingTimePicker.adapter = BookedTimeSpinnerAdapter(times)
    }

    override fun navigateToMovieBookingSeat(bookingStatus: BookingStatus) {
        val intent =
            MovieBookingSeatActivity.movieBookingSeatIntent(
                this@MovieBookingActivity,
                bookingStatus,
                theater,
            )
        startActivity(intent)
    }

    override fun showError(messageRes: Int) {
        AlertDialog.Builder(this)
            .setMessage(getString(messageRes))
            .setPositiveButton(R.string.error_dialog_okay, null)
            .show()
            .setCancelable(false)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.movie_booking)
    }

    private fun applyWindowInserts() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initIntentData() {
        movie =
            BuildVersion().getParcelableClass(
                intent,
                KEY_MOVIE, Movie::class,
            )
        theater =
            BuildVersion().getParcelableClass(
                intent,
                KEY_THEATER, Theater::class,
            )
    }

    private fun setUpPresenter() {
        presenter = MovieBookingPresenter(this@MovieBookingActivity)
        presenter.loadMovie(movie)
    }

    private fun setupDatePicker() {
        binding.bookingDatePicker.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = (parent.adapter as BookedDateSpinnerAdapter).getItem(position)
                    presenter.selectDate(selectedDate)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun setupTimePicker() {
        binding.bookingTimePicker.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedTime = (parent.adapter as BookedTimeSpinnerAdapter).getItem(position)
                    presenter.selectTime(selectedTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun setupMemberCount() {
        binding.bookingPlusMemberCount.setOnClickListener { presenter.increaseCount() }
        binding.bookingMinusMemberCount.setOnClickListener { presenter.decreaseCount() }
    }

    private fun setupBookingCompleteButton() {
        binding.bookingCompleteButton.setOnClickListener { presenter.confirmBooking() }
    }

    companion object {
        private const val KEY_MOVIE = "movie"
        private const val KEY_THEATER = "theater"

        fun movieBookingIntent(
            context: Context,
            movie: Movie,
            theater: Theater,
        ): Intent {
            return Intent(context, MovieBookingActivity::class.java)
                .apply {
                    putExtra(KEY_MOVIE, movie)
                    putExtra(KEY_THEATER, theater)
                }
        }
    }
}
