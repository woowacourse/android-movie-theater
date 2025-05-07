package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun initBooking()

        fun showMovie(movie: Movie)

        fun showBookableDates(dates: List<LocalDate>)

        fun showBookableTimes(times: List<LocalTime>)

        fun updateHeadCount(count: Int)

        fun navigateToSeats(ticket: MovieTicket)
    }

    interface Presenter {
        fun initializeBooking(screeningInfo: ScreeningInfo)

        fun selectDate(selectedDate: LocalDate)

        fun selectTime(selectedTime: LocalTime)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun confirmBooking()

        fun restoreBookingState(
            count: Int?,
            date: LocalDate?,
            time: LocalTime?,
        )
    }
}
