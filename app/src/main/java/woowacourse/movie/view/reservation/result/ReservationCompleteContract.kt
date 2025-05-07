package woowacourse.movie.view.reservation.result

import woowacourse.movie.domain.movieseat.Seats
import woowacourse.movie.view.reservation.Ticket

class ReservationCompleteContract {
    interface Presenter {
        fun fetchData(
            ticket: Ticket,
            seats: Seats,
        )
    }

    interface View {
        fun handleInvalidTicket()

        fun showTicketInfo(ticket: Ticket)

        fun showSeatsInfo(seats: String)

        fun showTicketMoney(moviePrice: Int)
    }
}
