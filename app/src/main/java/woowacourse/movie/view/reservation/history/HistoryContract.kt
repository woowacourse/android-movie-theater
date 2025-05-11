package woowacourse.movie.view.reservation.history

import woowacourse.movie.view.reservation.Ticket

interface HistoryContract {
    interface Presenter {
        fun loadTickets()

        fun onTicketSelected(index: Int)
    }

    interface View {
        fun showMoviesScreen(tickets: List<Ticket>)

        fun handleReservationComplete(ticket: Ticket)
    }
}
