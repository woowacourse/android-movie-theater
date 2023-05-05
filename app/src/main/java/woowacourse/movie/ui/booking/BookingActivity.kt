package woowacourse.movie.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import woowacourse.movie.model.main.MovieMapper.toUiModel
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.ui.seat.SeatActivity
import woowacourse.movie.util.formatScreenDate

class BookingActivity : AppCompatActivity(), BookingContract.View {

    private lateinit var binding: ActivityBookingBinding
    private val movie: MovieUiModel by lazy {
        MovieRepository.getMovie(
            movieId = intent.getLongExtra(MOVIE_ID, -1)
        ).toUiModel()
    }
    private val bookingPresenter: BookingContract.Presenter by lazy {
        BookingPresenter(
            bookingView = this,
            movie = movie,
        )
    }
    private val dateTimePresenter: DateTimeContract.Presenter by lazy {
        DateTimePresenter(
            startDate = movie.startDate,
            endDate = movie.endDate,
            view = binding.spinnerDateTime
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initTicketCountText()
        initTicketCountButtonClickListener()
        initCompleteButtonClickListener()
        initDateTimeSpinner()
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

    private fun initView() {
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

    private fun initTicketCountText() {
        bookingPresenter.initTicketCount()
    }

    private fun initTicketCountButtonClickListener() {
        binding.buttonBookingMinus.setOnClickListener {
            bookingPresenter.minusTicketCount()
        }
        binding.buttonBookingPlus.setOnClickListener {
            bookingPresenter.plusTicketCount()
        }
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

    private fun initDateTimeSpinner() {
        dateTimePresenter.initDateTimes()
    }

    override fun setTicketCountText(count: Int) {
        binding.textBookingTicketCount.text = count.toString()
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
        private const val TICKET_COUNT = "TICKET_COUNT"

        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }
}
