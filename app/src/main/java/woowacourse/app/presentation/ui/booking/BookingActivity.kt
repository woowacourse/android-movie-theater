package woowacourse.app.presentation.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.app.presentation.model.BookedMovieUiModel
import woowacourse.app.presentation.model.movie.MovieMapper
import woowacourse.app.presentation.model.movie.MovieMapper.toDomainModel
import woowacourse.app.presentation.model.movie.MovieMapper.toUiModel
import woowacourse.app.presentation.model.movie.MovieUiModel
import woowacourse.app.presentation.ui.seat.SeatActivity
import woowacourse.app.presentation.util.getParcelable
import woowacourse.app.presentation.util.shortToast
import woowacourse.domain.movie.Movie
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingBinding
import java.time.LocalDate
import java.time.LocalTime

class BookingActivity : AppCompatActivity(), BookingContract.View {
    private lateinit var binding: ActivityBookingBinding
    override lateinit var presenter: BookingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = BookingPresenter(this, getMovie())
        binding.presenter = presenter
        restoreData(savedInstanceState)
        initView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(TICKET_COUNT, presenter.getTicketCount())
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreTicketCount(savedInstanceState.getInt(TICKET_COUNT))
        }
    }

    private fun getMovie(): Movie {
        val movie = intent.getParcelable(MOVIE_ID, MovieUiModel::class.java)
        if (movie == null) {
            shortToast(R.string.error_no_such_movie)
            finish()
        }
        return movie!!.toDomainModel()
    }

    private fun initView() {
        presenter.initTicketCount()
        presenter.initDateTimes()
        setBookingPosterImage()
        showBackButton()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setBookingPosterImage() {
        binding.imageBookingPoster.setImageResource(MovieMapper.getPoster(presenter.movie.id))
    }

    override fun initDateTimes(dates: List<LocalDate>) {
        binding.spinnerDateTime.initDateItems(dates)
        presenter.fetchScreeningTimes()
    }

    override fun initScreeningTimes(fetchTimes: (screeningDate: LocalDate) -> List<LocalTime>) {
        binding.spinnerDateTime.initDateSelectedListener { fetchTimes(it) }
    }

    override fun showTicketCount(value: Int) {
        binding.textBookingTicketCount.text = value.toString()
    }

    override fun startSeatActivity(ticketCount: Int) {
        val dateTime = binding.spinnerDateTime.selectedDateTime
        val bookedMovieUiModel =
            BookedMovieUiModel(presenter.movie.toUiModel(), 0, ticketCount, dateTime)
        startActivity(SeatActivity.getIntent(this, bookedMovieUiModel))
        finish()
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        private const val TICKET_COUNT = "TICKET_COUNT"

        fun getIntent(context: Context, movieUiModel: MovieUiModel): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                putExtra(MOVIE_ID, movieUiModel)
            }
        }
    }
}
