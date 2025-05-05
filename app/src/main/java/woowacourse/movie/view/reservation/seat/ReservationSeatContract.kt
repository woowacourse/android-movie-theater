package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seats

interface ReservationSeatContract {
    interface Present {
        fun fetchData(ticket: Ticket)

        fun updateMoney()

        fun selectSeat(position: Position)

        fun deselectSeat(position: Position)

        fun getCurrentSeat(): Seats

        fun restoreSeat(seats: Seats)
    }

    interface View {
        fun handleInvalidTicket()

        fun setSeatTag()

        fun setSeatInit()

        fun setReservationButton(onClickConfirm: () -> Unit)

        fun setSeatClickListener()

        fun showMovieName(movieName: String)

        fun showTicketMoney(moviePrice: Int)

        fun selectSeatView(position: Position)

        fun deselectSeatView(position: Position)

        fun showReservationDialog(
            ticket: Ticket,
            seats: Seats,
        )

        fun navigateToReservationComplete(
            ticket: Ticket,
            seats: Seats,
        )

        fun selectableButton()

        fun deSelectableButton()
    }
}
