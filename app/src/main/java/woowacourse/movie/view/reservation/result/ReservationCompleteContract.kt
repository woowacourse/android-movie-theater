package woowacourse.movie.view.reservation.result

import woowacourse.movie.domain.Ticket
import woowacourse.movie.view.reservation.TicketUi

class ReservationCompleteContract {
    interface Presenter {
        fun fetchData(ticket: Ticket)
    }

    interface View {
        fun handleInvalidTicket()

        fun showTicketInfo(ticketUi: TicketUi)

        fun showSeatsInfo(seats: String)

        fun showTicketMoney(moviePrice: Int)
    }
}
