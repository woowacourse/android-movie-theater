package woowacourse.movie.ui.booking.contract

import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.theater.Theater
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {
    interface Presenter {
        fun decreaseHeadcount()

        fun increaseHeadcount()

        fun completeBooking()

        fun refreshMovieInfo()

        fun refreshHeadcountDisplay()

        fun setupDateSpinner()

        fun setupTimeSpinner()

        fun loadState(
            theater: Theater,
            headcount: Headcount,
            movie: Movie,
            selectedDatePosition: Int,
            selectedTimePosition: Int
        )

        fun loadSelectedDate(selectedDate: LocalDate, selectedDatePosition: Int)

        fun loadSelectedTime(selectedTimePosition: Int)

        fun loadSelectedDateTime(selectedLocalDateTime: LocalDateTime)
    }

    interface View {
        fun setMovieInfoViews(movie: Movie)

        fun updateHeadcountDisplay(headcount: Headcount)

        fun setDateSpinner(
            spinnerItems: List<LocalDate>,
            position: Int,
        )

        fun setTimeSpinner(
            spinnerItems: List<LocalTime>,
            position: Int,
        )

        fun startBookingSeatActivity(
            movieTitle: String,
            dateTime: LocalDateTime,
            headcount: Headcount,
            theater: Theater,
        )
    }
}
