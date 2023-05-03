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
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.util.keyError
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDetailActivity : BackKeyActionBarActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var movie: MovieState

    private lateinit var dateTimeSpinner: DateTimeSpinner
    private lateinit var reservationCounter: ReservationCounter

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        movie = intent.getParcelableExtraCompat(KEY_MOVIE) ?: return keyError(KEY_MOVIE)
        binding.movie = movie

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            dateTimeSpinner = DateTimeSpinner(
                binding,
                movie,
            )
            reservationCounter = ReservationCounter(binding)
        }
        binding.reservationConfirm.setOnClickListener { navigateSeatSelectActivity() }
    }

    private fun navigateSeatSelectActivity() {
        val dateTime = dateTimeSpinner.selectDateTime
        val reservationState =
            ReservationState(movie, dateTime, reservationCounter.count)
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
        dateTimeSpinner = DateTimeSpinner(
            binding,
            movie,
            LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        )
        reservationCounter = ReservationCounter(binding, restoreCount)
    }

    companion object {
        fun getIntent(context: Context, movie: MovieState): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(KEY_MOVIE, movie)
            return intent
        }

        private const val KEY_MOVIE = "key_movie"
        private const val KEY_COUNT = "key_reservation_count"
        private const val KEY_DATE = "key_reservation_date"
        private const val KEY_TIME = "key_reservation_time"
    }
}
