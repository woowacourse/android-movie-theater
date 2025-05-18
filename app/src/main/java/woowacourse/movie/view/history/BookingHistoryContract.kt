package woowacourse.movie.view.history

import woowacourse.movie.domain.model.Ticket

interface BookingHistoryContract {
    interface View {
        fun showTickets(tickets: List<Ticket>)

        fun moveToTicketDetail(ticketId: Long)

        fun showMessage()
    }

    interface Presenter {
        fun loadHistory()
    }
}
