package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import woowacourse.movie.R
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.ui.seat.SeatSelectActivity
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.util.keyError

class MovieDetailActivity : BackKeyActionBarActivity(), MovieDetailContract.View {
    override var presenter: MovieDetailContract.Presenter = MovieDetailPresenter(this)

    private lateinit var movie: MovieState
    private lateinit var cinemaName: String

    private lateinit var movieInfo: MovieInfo
    private lateinit var dateTimeSpinner: DateTimeSpinner
    private lateinit var view: TicketCounter

    private lateinit var reservationConfirm: Button

    private val rootView by lazy { window.decorView.rootView }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_movie_detail)
        movie = intent.getParcelableExtraCompat(KEY_MOVIE) ?: return keyError(KEY_MOVIE)
        cinemaName = intent.getStringExtra(KEY_CINEMA_NAME) ?: return keyError(KEY_CINEMA_NAME)

        movieInfo = MovieInfo(rootView).also { it.setMovieState(movie) }
        when (savedInstanceState) {
            null -> initInstanceState()
            else -> restoreInstanceState(savedInstanceState)
        }
        reservationConfirm = findViewById(R.id.reservation_confirm)
        reservationConfirm.setOnClickListener { navigateSeatSelectActivity() }
    }

    override fun setCounterText(count: Int) {
        view.setCounterText(count)
    }

    private fun navigateSeatSelectActivity() {
        val seatSelectState = SeatSelectState(
            movie,
            dateTimeSpinner.getSelectDateTime(),
            presenter.count
        )
        SeatSelectActivity.startActivity(this, cinemaName, seatSelectState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dateTime = dateTimeSpinner.getSelectDateTime()
        outState.putSerializable(KEY_DATE, dateTime.toLocalDate())
        outState.putSerializable(KEY_TIME, dateTime.toLocalTime())
        outState.putParcelable(KEY_COUNT, presenter.count)
    }

    private fun initInstanceState() {
        dateTimeSpinner = DateTimeSpinner(
            rootView,
            movie,
            presenter::getMovieRunningDates,
            presenter::getMovieRunningTimes
        )
        view = TicketCounter(rootView, presenter)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        val restoreSelectDate: LocalDate =
            savedInstanceState.getSerializableCompat(KEY_DATE) ?: return keyError(KEY_DATE)
        val restoreSelectTime: LocalTime =
            savedInstanceState.getSerializableCompat(KEY_TIME) ?: return keyError(KEY_TIME)
        val restoreCount: CountState =
            savedInstanceState.getParcelableCompat(KEY_COUNT) ?: return keyError(KEY_COUNT)
        dateTimeSpinner = DateTimeSpinner(
            rootView,
            movie,
            presenter::getMovieRunningDates,
            presenter::getMovieRunningTimes,
            LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        )
        view = TicketCounter(rootView, presenter, restoreCount)
    }

    companion object {
        private const val KEY_COUNT = "key_reservation_count"
        private const val KEY_DATE = "key_reservation_date"
        private const val KEY_TIME = "key_reservation_time"
        private const val KEY_MOVIE = "key_movie"
        private const val KEY_CINEMA_NAME = "key_cinema_name"

        fun startActivity(context: Context, cinemaName: String, movie: MovieState) {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(KEY_CINEMA_NAME, cinemaName)
                putExtra(KEY_MOVIE, movie)
            }
            context.startActivity(intent)
        }
    }
}
