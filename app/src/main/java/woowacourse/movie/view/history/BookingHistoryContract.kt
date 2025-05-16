package woowacourse.movie.view.history

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingHistoryContract {
    interface View {
        fun showTickets(tickets: List<Ticket>)

        fun moveToBookingComplete(ticket: Ticket)
    }

    interface Presenter {
        fun loadTickets()

        fun selectHistory(ticket: Ticket)
    }
}
