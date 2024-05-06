package woowacourse.movie.presentation.ticketingResult

import woowacourse.movie.model.Ticket

interface TicketingResultContract {
    interface View {
        fun displayTicketInfo(
            ticket: Ticket,
            theaterName: String,
        )

        fun showToastMessage(message: String?)
    }

    interface Presenter {
        fun loadTicketInfo(movieTicket: Ticket?)
    }
}
