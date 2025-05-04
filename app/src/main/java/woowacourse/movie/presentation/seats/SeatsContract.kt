package woowacourse.movie.presentation.seats

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat

interface SeatsContract {
    interface View {
        fun showMovieInfo(movie: Movie)

        fun showTotalPrice(price: Int)

        fun updateSeatSelectionState(
            seat: Seat,
            isSelected: Boolean,
        )

        fun updateConfirmButtonState(isEnabled: Boolean)

        fun navigateToSummary(ticket: Ticket)
    }

    interface Presenter {
        fun loadSeatSelect()

        fun selectSeat(seat: Seat)

        fun finishBooking()

        fun restoreTicket(ticket: Ticket)
    }
}
