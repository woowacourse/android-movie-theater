package woowacourse.movie.view.reservation.seat

import android.os.Bundle
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seats

interface ReservationSeatContract {
    interface Present {
        fun fetchData(ticket: Ticket)

        fun updateMoney()

        fun selectSeat(position: Position)

        fun deselectSeat(position: Position)

        fun onSaveState(outState: Bundle)

        fun onRestoreState(outState: Bundle)

        fun handle()
    }

    interface View {
        fun handleInvalidTicket()

        fun showMovieName(movieName: String)

        fun showTicketMoney(seatsPrice: Int)

        fun selectSeatView(position: Position)

        fun deselectSeatView(position: Position)

        fun handleReservationComplete(
            ticket: Ticket,
            seats: Seats,
        )

        fun setButton(isSelectable: Boolean)
    }
}
