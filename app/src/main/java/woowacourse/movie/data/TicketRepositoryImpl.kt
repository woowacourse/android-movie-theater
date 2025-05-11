package woowacourse.movie.data

import woowacourse.movie.MyApp
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.TicketRepository

class TicketRepositoryImpl(
    private val dao: TicketDao = TicketDatabase.getDataBase(MyApp.applicationContext).ticketDao(),
) : TicketRepository {
    override fun getAll(): List<Ticket> {
        return dao.getAll().toDomain()
    }

    override fun insertAll(vararg ticket: Ticket) {
        dao.insertAll(*ticket.map { it.toEntity() }.toTypedArray())
    }

    private fun List<TicketEntity>.toDomain() =
        this.map { entity ->
            Ticket(
                entity.title,
                entity.date,
                entity.personnel,
                entity.theaterName,
                entity.seats,
            )
        }

    private fun Ticket.toEntity() =
        TicketEntity(
            title = this.title,
            date = this.date,
            personnel = this.personnel,
            theaterName = this.theaterName,
            seats = this.seats,
        )
}
