package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import woowacourse.movie.databinding.ActivityMovieDetailBinding
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

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var movie: MovieState
    private lateinit var cinemaName: String

    private lateinit var dateTimeSpinner: DateTimeSpinner

    override fun onCreateView(savedInstanceState: Bundle?) {
        setUpParcelable()
        setUpBinding()
        setUpInstanceState(savedInstanceState)
        setContentView(binding.root)
    }

    private fun setUpParcelable() {
        movie = intent.getParcelableExtraCompat(KEY_MOVIE) ?: return keyError(KEY_MOVIE)
        cinemaName = intent.getStringExtra(KEY_CINEMA_NAME) ?: return keyError(KEY_CINEMA_NAME)
    }

    private fun setUpBinding() {
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        binding.movieDetailView = this
        binding.movieDetailPreseter = presenter
        binding.movie = movie
    }

    private fun setUpInstanceState(savedInstanceState: Bundle?) {
        when (savedInstanceState) {
            null -> initInstanceState()
            else -> restoreInstanceState(savedInstanceState)
        }
    }

    private fun initInstanceState() {
        dateTimeSpinner = DateTimeSpinner(
            binding,
            movie,
            presenter::getMovieRunningDates,
            presenter::getMovieRunningTimes
        )
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        val restoreSelectDate: LocalDate =
            savedInstanceState.getSerializableCompat(KEY_DATE) ?: return keyError(KEY_DATE)
        val restoreSelectTime: LocalTime =
            savedInstanceState.getSerializableCompat(KEY_TIME) ?: return keyError(KEY_TIME)
        val restoreCount: CountState =
            savedInstanceState.getParcelableCompat(KEY_COUNT) ?: return keyError(KEY_COUNT)
        dateTimeSpinner = DateTimeSpinner(
            binding,
            movie,
            presenter::getMovieRunningDates,
            presenter::getMovieRunningTimes,
            LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        )

        presenter.count = restoreCount
    }

    override fun setCounterText(count: Int) {
        binding.count.text = count.toString()
    }

    override fun navigateSeatSelectActivity() {
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
