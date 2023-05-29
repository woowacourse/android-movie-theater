package woowacourse.movie.feature.reservation.reserve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.BackKeyActionBarActivity
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.reservation.reserve.count.TicketCounterView
import woowacourse.movie.feature.reservation.reserve.selection.DateTimeSpinner
import woowacourse.movie.feature.seat.SeatSelectActivity
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.util.keyError
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieDetailActivity : BackKeyActionBarActivity(), MovieDetailContract.View {

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var theaterMovie: SelectTheaterAndMovieState

    private lateinit var dateTimeSpinner: DateTimeSpinner
    private lateinit var reservationCounter: TicketCounterView

    private val presenter: MovieDetailContract.Presenter = MovieDetailPresenter(this)
    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        theaterMovie = intent.getParcelableExtraCompat(KEY_SELECT_THEATER_MOVIE)
            ?: return keyError(KEY_SELECT_THEATER_MOVIE)
        binding.theaterMovie = theaterMovie

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            dateTimeSpinner = DateTimeSpinner(binding, theaterMovie)
            reservationCounter = TicketCounterView(binding)
        }

        binding.counterPresenter = reservationCounter.presenter

        binding.reservationConfirm.setOnClickListener {
            presenter.navigateSeatSelectActivity(
                theaterMovie,
                dateTimeSpinner.selectedDateTime,
                reservationCounter.count
            )
        }

        binding.movieScreeningPeriod.text = DateTimeFormatters.convertToDateTildeDate(this, theaterMovie.movie.startDate, theaterMovie.movie.endDate)
    }

    override fun showSeatSelectActivity(reservationState: SelectReservationState) {
        val intent = SeatSelectActivity.getIntent(this, reservationState)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dateTime = dateTimeSpinner.selectedDateTime
        outState.putSerializable(KEY_DATE, dateTime.toLocalDate())
        outState.putSerializable(KEY_TIME, dateTime.toLocalTime())
        outState.putInt(KEY_COUNT, reservationCounter.count)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        val restoreSelectDate: LocalDate =
            savedInstanceState.getSerializableCompat(KEY_DATE) ?: return keyError(KEY_DATE)
        val restoreSelectTime: LocalTime =
            savedInstanceState.getSerializableCompat(KEY_TIME) ?: return keyError(KEY_TIME)
        val restoreCount: Int = savedInstanceState.getInt(KEY_COUNT)

        val restoreDateTime = LocalDateTime.of(restoreSelectDate, restoreSelectTime)
        dateTimeSpinner = DateTimeSpinner(binding, theaterMovie, restoreDateTime)
        reservationCounter = TicketCounterView(binding, restoreCount)
    }

    companion object {
        private const val KEY_SELECT_THEATER_MOVIE = "key_theater_movie"
        private const val KEY_COUNT = "key_reservation_count"
        private const val KEY_DATE = "key_reservation_date"
        private const val KEY_TIME = "key_reservation_time"

        fun getIntent(context: Context, theaterMovie: SelectTheaterAndMovieState): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(KEY_SELECT_THEATER_MOVIE, theaterMovie)
            return intent
        }
    }
}
