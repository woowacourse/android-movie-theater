package woowacourse.movie.view.reservation.history

import woowacourse.movie.domain.Ticket

interface HistoryContract {
    interface View {
        fun showTickets(tickets: List<Ticket>)

        fun navigateToReservationComplete(ticket: Ticket)
    }

    interface Presenter {
        fun loadTickets()

        fun onTicketClicked(ticket: Ticket)
    }
}
