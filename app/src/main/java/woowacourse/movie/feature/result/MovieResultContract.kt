package woowacourse.movie.feature.result

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.BasePresenter

interface MovieResultContract {
    interface View {
        fun displayTicket(ticket: Ticket)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadTicket(
            ticketRepository: TicketRepository,
            ticketId: Long,
        )
    }
}
