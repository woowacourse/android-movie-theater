package woowacourse.movie.presentation.ticket.detail

import woowacourse.movie.domain.model.Ticket

interface TicketDetailContract {
    interface View {
        fun showTicketInfo(ticket: Ticket)
    }

    interface Presenter {
        fun loadBookingResult()
    }
}
