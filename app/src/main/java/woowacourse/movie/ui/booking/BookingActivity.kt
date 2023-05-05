package woowacourse.movie.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ui.seat.SeatActivity
import woowacourse.movie.util.formatScreenDate
import java.time.LocalDate
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {

    private lateinit var binding: ActivityBookingBinding
    private val movieId: Long by lazy {
        intent.getLongExtra(MOVIE_ID, -1)
    }
    private val theaterId: Long by lazy {
        intent.getLongExtra(THEATER_ID, 0)
    }
    private val bookingPresenter: BookingContract.Presenter by lazy {
        BookingPresenter(
            view = this,
            movieId = movieId,
            theaterId = theaterId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking)
        binding.presenter = bookingPresenter

        bookingPresenter.initMovie()
        bookingPresenter.initTicketCount()
        bookingPresenter.initDateTimes()
        initCompleteButtonClickListener()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)

        setTicketCountText(
            count = savedInstanceState.getInt(TICKET_COUNT)
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run {
            putInt(TICKET_COUNT, binding.textBookingTicketCount.text.toString().toInt())
        }
    }

    override fun initView(movie: MovieUiModel) {
        binding.imageBookingPoster.setImageResource(movie.poster)
        binding.textBookingTitle.text = movie.title
        binding.textBookingScreeningDate.text =
            getString(
                R.string.screening_date,
                movie.startDate.formatScreenDate(),
                movie.endDate.formatScreenDate(),
            )
        binding.textBookingRunningTime.text = getString(R.string.running_time, movie.runningTime)
        binding.textBookingDescription.text = movie.description
        showBackButton()
    }

    override fun setTicketCountText(count: Int) {
        binding.textBookingTicketCount.text = count.toString()
    }

    override fun setTimes(screeningTimes: List<LocalTime>) {
        binding.spinnerDateTime.setTimes(screeningTimes)
    }

    override fun setDates(screeningDates: List<LocalDate>) {
        binding.spinnerDateTime.setDates(screeningDates)
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initCompleteButtonClickListener() {
        binding.buttonBookingComplete.setOnClickListener {
            val bookedMovie =
                bookingPresenter.createBookedMovie(binding.spinnerDateTime.selectedDateTime)

            startActivity(SeatActivity.getIntent(this, bookedMovie))
            finish()
        }
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val THEATER_ID = "THEATER_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"

        fun getIntent(
            context: Context,
            movieId: Long,
            theaterId: Long,
        ): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(THEATER_ID, theaterId)
            }
        }
    }
}
