package woowacourse.movie.feature.reservation.reserve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.domain.usecase.GetMovieRunningDateUseCase
import com.example.domain.usecase.GetMovieRunningTimeUseCase
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.main.MainActivity.Companion.KEY_MOVIE
import woowacourse.movie.feature.reservation.reserve.count.TicketCounterView
import woowacourse.movie.feature.reservation.reserve.selection.DateTimeSpinner
import woowacourse.movie.feature.seat.SeatSelectActivity
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.util.keyError
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDetailActivity : BackKeyActionBarActivity() {

    private val binding: ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }
    private val movieInformationView: MovieInformationView by lazy { MovieInformationView(binding) }
    private val ticketCounterView: TicketCounterView by lazy { TicketCounterView(binding) }

    private val getMovieRunningDateUseCase = GetMovieRunningDateUseCase()
    private val getMovieRunningTimeUseCase = GetMovieRunningTimeUseCase()

    private lateinit var movie: MovieState
    private lateinit var dateTimeSpinner: DateTimeSpinner

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        movie = intent.getParcelableExtraCompat(KEY_MOVIE) ?: return keyError(KEY_MOVIE)
        movieInformationView.setContents(movie)
        ticketCounterView.set(savedInstanceState)
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            dateTimeSpinner = DateTimeSpinner(
                binding,
                movie,
                ::getMovieRunningDates,
                ::getMovieRunningTimes
            )
        }
        binding.reservationConfirm.setOnClickListener { navigateSeatSelectActivity() }
    }

    private fun navigateSeatSelectActivity() {
        val dateTime = dateTimeSpinner.selectedDateTime
        SeatSelectActivity.startActivity(this, movie, dateTime, ticketCounterView.count)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dateTime = dateTimeSpinner.selectedDateTime
        outState.putSerializable(KEY_DATE, dateTime.toLocalDate())
        outState.putSerializable(KEY_TIME, dateTime.toLocalTime())
        outState.putInt(KEY_COUNT, ticketCounterView.count)
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
            ::getMovieRunningDates,
            ::getMovieRunningTimes,
            LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        )
    }

    private fun getMovieRunningDates(movie: MovieState) =
        getMovieRunningDateUseCase(movie.asDomain())

    private fun getMovieRunningTimes(date: LocalDate) =
        getMovieRunningTimeUseCase(date)

    companion object {
        private const val KEY_COUNT = "key_reservation_count"
        private const val KEY_DATE = "key_reservation_date"
        private const val KEY_TIME = "key_reservation_time"
        internal const val KEY_TICKETS = "key_reservation"

        fun startActivity(context: Context, movie: MovieState) {
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE, movie)
            }
            context.startActivity(intent)
        }
    }
}
