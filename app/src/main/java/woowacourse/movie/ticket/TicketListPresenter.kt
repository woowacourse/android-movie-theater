package woowacourse.movie.ticket

import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import kotlin.concurrent.thread

class TicketListPresenter(
    private val view: TicketListContract.View,
    private val movieDatabase: MovieDatabase,
) : TicketListContract.Presenter {
    private val reservations: MutableList<Ticket> = mutableListOf()

    override fun initializeData() {
        thread {
            val tickets = movieDatabase.TicketDao().getAllTickets()
            reservations.addAll(tickets.map { it.toDomain() })
        }.join()
        view.setUpReservationList(reservations.map { it.toUiModel() })
    }
}
