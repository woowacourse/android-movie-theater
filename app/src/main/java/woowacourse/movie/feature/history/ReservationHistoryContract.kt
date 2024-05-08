package woowacourse.movie.feature.history

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.BasePresenter

interface ReservationHistoryContract {
    interface View {
        fun displayTickets(tickets: List<Ticket>)
    }

    interface Presenter : BasePresenter {
        fun loadTickets(ticketRepository: TicketRepository)
    }
}
