package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Ticket
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        fun showMovie(movie: Movie)

        fun showHeadCount(count: Int)

        fun updateDecreaseButtonState(isEnabled: Boolean)

        fun updateIncreaseButtonState(isEnabled: Boolean)

        fun showBookableDates(
            dates: List<LocalDate>,
            selectedDate: LocalDate,
        )

        fun showBookableTimes(
            times: List<LocalTime>,
            selectedTime: LocalTime,
        )

        fun navigateToSeatSelect(ticket: Ticket)
    }

    interface Presenter {
        fun loadBooking()

        fun selectScreeningDate(date: LocalDate)

        fun selectScreeningTime(time: LocalTime)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun confirmBooking()

        fun restoreTicket(ticket: Ticket)
    }
}
