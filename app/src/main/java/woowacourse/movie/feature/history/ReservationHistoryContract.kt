package woowacourse.movie.feature.history

import woowacourse.movie.db.ticket.Ticket

interface ReservationHistoryContract {
    interface Presenter {
        fun loadTicket()

        fun deliverTicketId(ticketId: Long?)
    }

    interface View {
        fun showReservationHistory(tickets: List<Ticket>)

        fun navigateToReservationInformation(ticketId: Long?)
    }
}
