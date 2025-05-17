package woowacourse.movie.ui.booking

import java.time.LocalDate
import java.time.LocalTime
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Theater

interface BookingContract {
    interface Presenter {
        fun loadBookingInfos(
            theater: Theater?,
            movieId: Long,
        )

        fun updateScreeningDate(screeningDate: LocalDate)

        fun updateScreeningTime(screeningTime: LocalTime)

        fun updateScreeningTimeSpinner()

        fun decreaseHeadcount()

        fun increaseHeadcount()

        fun completeBooking()

        fun restoreBookingInfos(
            count: Int,
            selectedDatePosition: Int,
            selectedTimePosition: Int,
        )
    }

    interface View {
        fun showMovie(movie: Movie)

        fun displayScreeningDateSpinner(dates: List<LocalDate>)

        fun displayScreeningTimeSpinner(times: List<LocalTime>)

        fun displayScreeningTimeSpinnerItems(times: List<LocalTime>)

        fun showScreeningDate(position: Int)

        fun showScreeningTime(position: Int)

        fun showHeadCount(headcount: Headcount)

        fun moveToSelectSeat(
            movieId: Long,
            movieSchedule: MovieSchedule,
            headcount: Headcount,
            theaterName: String,
        )
    }
}
