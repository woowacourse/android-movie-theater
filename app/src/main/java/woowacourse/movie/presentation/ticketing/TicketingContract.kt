package woowacourse.movie.presentation.ticketing

import android.widget.AdapterView.OnItemSelectedListener
import woowacourse.movie.model.Movie
import java.time.LocalDate
import java.time.LocalTime

interface TicketingContract {
    interface View {
        fun displayMovieDetail(movie: Movie)

        fun displayTicketCount(count: Int)

        fun setUpDateSpinners(
            screeningDates: List<LocalDate>,
            listener: OnItemSelectedListener,
        )

        fun setUpTimeSpinners(
            screeningTimes: List<LocalTime>,
            savedTimePosition: Int?,
        )

        fun navigate(
            movieId: Long,
            count: Int,
            theaterId: Long,
        )

        fun showErrorMessage(message: String?)
    }

    interface Presenter {
        fun loadMovieData(
            movieId: Long,
            theaterId: Long,
        )

        fun updateCount(savedCount: Int)

        fun updateSelectedTimePosition(savedTimePosition: Int)

        fun decreaseCount()

        fun increaseCount()

        fun navigate()
    }
}
