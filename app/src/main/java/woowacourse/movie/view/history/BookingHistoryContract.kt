package woowacourse.movie.view.history

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingHistoryContract {
    interface View {
        fun showTickets(tickets: List<Ticket>)

        fun moveToBookingComplete(ticket: Ticket)
    }

    interface Presenter {
        fun loadTickets(tickets: List<Ticket>)

        fun selectHistory(ticket: Ticket)
    }
}
