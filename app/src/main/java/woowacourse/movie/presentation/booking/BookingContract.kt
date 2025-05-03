package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.movie.Movie
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

        fun navigateToSeats(ticket: Ticket)
    }

    interface Presenter {
        fun loadBooking()

        fun onDateSelected(date: LocalDate)

        fun onTimeSelected(time: LocalTime)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun confirmBooking()

        fun restoreTicket(ticket: Ticket)
    }
}
