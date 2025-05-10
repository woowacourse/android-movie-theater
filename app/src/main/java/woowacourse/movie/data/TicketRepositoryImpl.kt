package woowacourse.movie.data

import woowacourse.movie.MyApp
import woowacourse.movie.domain.TicketRepository
import woowacourse.movie.view.reservation.Ticket

class TicketRepositoryImpl(
    private val dao: TicketDao = TicketDatabase.getDataBase(MyApp.applicationContext).ticketDao(),
) : TicketRepository {
    override fun getAll(): List<Ticket> {
        return dao.getAll().toUiModel()
    }

    override fun insertAll(vararg ticket: Ticket) {
        dao.insertAll(*ticket.map { it.toEntity() }.toTypedArray())
    }

    private fun List<TicketEntity>.toUiModel() =
        this.map { entity ->
            Ticket(
                entity.title,
                entity.date,
                entity.personnel,
                entity.theaterName,
            )
        }

    private fun Ticket.toEntity() =
        TicketEntity(
            title = this.title,
            date = this.date,
            personnel = this.personnel,
            theaterName = this.theaterName,
        )
}
