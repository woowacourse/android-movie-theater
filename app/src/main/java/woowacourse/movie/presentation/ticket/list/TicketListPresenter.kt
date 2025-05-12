package woowacourse.movie.presentation.ticket.list

import android.content.Context
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.repository.LocalTicketRepository
import woowacourse.movie.data.repository.TicketRepository
import woowacourse.movie.domain.model.Ticket

class TicketListPresenter(
    private val view: TicketListContract.View,
    context: Context,
    private val ticketRepository: TicketRepository =
        LocalTicketRepository(MovieDatabase.getDatabase(context)),
) : TicketListContract.Presenter {
    override fun loadTicketList() {
        view.showTicketList(ticketRepository.getAll())
    }

    override fun selectTicket(ticket: Ticket) {
        view.navigateToTicketDetail(ticket)
    }
}
