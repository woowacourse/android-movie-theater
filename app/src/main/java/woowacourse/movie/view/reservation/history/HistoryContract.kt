package woowacourse.movie.view.reservation.history

import woowacourse.movie.view.reservation.TicketUi

interface HistoryContract {
    interface Presenter {
        fun loadTickets()

        fun onTicketSelected(index: Int)
    }

    interface View {
        fun showMoviesScreen(ticketUis: List<TicketUi>)

        fun handleReservationComplete(ticketUi: TicketUi)
    }
}
