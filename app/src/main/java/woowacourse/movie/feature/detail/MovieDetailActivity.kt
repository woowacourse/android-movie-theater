package woowacourse.movie.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.common.BackKeyActionBarActivity
import woowacourse.movie.feature.detail.counter.ReservationCounter
import woowacourse.movie.feature.detail.dateTime.DateTimeSpinner
import woowacourse.movie.feature.seatSelect.SeatSelectActivity
import woowacourse.movie.model.CountState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.TheaterMovieState
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.util.keyError
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDetailActivity : BackKeyActionBarActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding

    // Todo:
    private lateinit var theaterMovie: TheaterMovieState

    private lateinit var dateTimeSpinner: DateTimeSpinner
    private lateinit var reservationCounter: ReservationCounter

    private val presenter: MovieDetailContract.Presenter = MovieDetailPresenter(this)
    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        theaterMovie =
            intent.getParcelableExtraCompat(KEY_THEATER_MOVIE) ?: return keyError(KEY_THEATER_MOVIE)
        binding.theaterMovie = theaterMovie

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            dateTimeSpinner = DateTimeSpinner(binding, theaterMovie)
            reservationCounter = ReservationCounter(binding)
        }

        binding.counterPresenter = reservationCounter.presenter
        binding.dateTimePresenter = dateTimeSpinner.presenter

        binding.reservationConfirm.setOnClickListener {
            presenter.clickConfirm(
                theaterMovie.movie,
                dateTimeSpinner.selectDateTime,
                reservationCounter.count
            )
        }
    }

    override fun navigateSeatSelect(reservationState: ReservationState) {
        val intent = SeatSelectActivity.getIntent(this, reservationState)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dateTime = dateTimeSpinner.selectDateTime
        outState.putSerializable(KEY_DATE, dateTime.toLocalDate())
        outState.putSerializable(KEY_TIME, dateTime.toLocalTime())
        outState.putParcelable(KEY_COUNT, reservationCounter.count)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        val restoreSelectDate: LocalDate =
            savedInstanceState.getSerializableCompat(KEY_DATE) ?: return keyError(KEY_DATE)
        val restoreSelectTime: LocalTime =
            savedInstanceState.getSerializableCompat(KEY_TIME) ?: return keyError(KEY_TIME)
        val restoreCount: CountState =
            savedInstanceState.getParcelableCompat(KEY_COUNT) ?: return keyError(KEY_COUNT)

        val restoreDateTime = LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        dateTimeSpinner = DateTimeSpinner(binding, theaterMovie, restoreDateTime)
        reservationCounter = ReservationCounter(binding, restoreCount)
    }

    companion object {
        fun getIntent(context: Context, theaterMovie: TheaterMovieState): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(KEY_THEATER_MOVIE, theaterMovie)
            return intent
        }

        private const val KEY_THEATER_MOVIE = "key_theater_movie"
        private const val KEY_COUNT = "key_reservation_count"
        private const val KEY_DATE = "key_reservation_date"
        private const val KEY_TIME = "key_reservation_time"
    }
}
