package woowacourse.movie.view.history

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingHistoryContract {
    interface View {
        fun showTickets(tickets: List<Ticket>)

        fun moveToBookingComplete()
    }

    interface Presenter {
        fun loadTickets(tickets: List<Ticket>)

        fun onHistorySelected()
    }
}
