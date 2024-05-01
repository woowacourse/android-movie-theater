package woowacourse.movie.presentation.ticketing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTicketingBinding
import woowacourse.movie.model.Count
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.seatSelection.SeatSelectionActivity
import woowacourse.movie.repository.DummyTheaterList
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketingActivity : AppCompatActivity(), TicketingContract.View {
    private lateinit var ticketingPresenter: TicketingPresenter
    private val countText by lazy { findViewById<TextView>(R.id.tv_count) }
    private val movieDateAdapter: ArrayAdapter<LocalDate> by lazy {
        ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        )
    }
    private val movieTimeAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        )
    }
    private lateinit var binding: ActivityTicketingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticketing)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getLongExtra(EXTRA_MOVIE_ID, EXTRA_DEFAULT_MOVIE_ID)
        val theaterId = intent.getLongExtra(EXTRA_THEATER_ID, EXTRA_DEFAULT_THEATER_ID)

        ticketingPresenter = TicketingPresenter(this, MovieRepository(), DummyTheaterList)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticketing)

        binding.presenter = ticketingPresenter

        ticketingPresenter.loadMovieData(movieId, theaterId)
    }

    override fun displayMovieDetail(movie: Movie) {
        binding.movie = movie
        binding.ivThumbnail.setImageResource(movie.thumbnail)
    }

    override fun bindTicketCount(count: Count) {
        binding.count = count
    }

    override fun updateTicketCount() {
        binding.invalidateAll()
    }

    override fun setUpDateSpinners(screeningDates: List<LocalDate>) {
        movieDateAdapter.addAll(screeningDates)
        binding.spDate.adapter = movieDateAdapter
    }

    override fun setUpTimeSpinners(
        screeningTimes: List<LocalTime>,
        savedTimePosition: Int?,
    ) {
        movieTimeAdapter.clear()
        movieTimeAdapter.addAll(screeningTimes.map { it.format(DateTimeFormatter.ofPattern("kk:mm")) })
        binding.spTimeSlot.adapter = movieTimeAdapter
        savedTimePosition?.let { binding.spTimeSlot.setSelection(it) }
    }

    override fun navigate(
        movieId: Long,
        count: Int,
        theaterId: Long,
    ) {
        val screeningDateTime = "${binding.spDate.selectedItem} ${binding.spTimeSlot.selectedItem}"
        startActivity(
            SeatSelectionActivity.createIntent(this, movieId, count, screeningDateTime, theaterId),
        )
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SAVED_COUNT, countText.text.toString().toInt())
        outState.putInt(KEY_SELECTED_TIME_POSITION, binding.spTimeSlot.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedCount = savedInstanceState.getInt(KEY_SAVED_COUNT, SAVED_DEFAULT_VALUE)
        if (savedCount != SAVED_DEFAULT_VALUE) {
            ticketingPresenter.updateCount(savedCount)
        }

        val savedTimePosition = savedInstanceState.getInt(KEY_SELECTED_TIME_POSITION)
        if (savedTimePosition != SAVED_DEFAULT_VALUE) {
            ticketingPresenter.updateSelectedTimePosition(savedTimePosition)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movie_id"
        const val EXTRA_THEATER_ID = "theater_id"
        const val EXTRA_DEFAULT_MOVIE_ID = -1L
        const val EXTRA_DEFAULT_THEATER_ID = -1L
        const val KEY_SAVED_COUNT = "saved_count"
        const val SAVED_DEFAULT_VALUE = -1
        const val KEY_SELECTED_TIME_POSITION = "selected_time_position"

        fun createIntent(
            context: Context,
            theaterId: Long,
            movieId: Long,
        ): Intent {
            return Intent(context, TicketingActivity::class.java).apply {
                putExtra(EXTRA_THEATER_ID, theaterId)
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }
}
