package woowacourse.movie.data.repository

import woowacourse.movie.data.ticket.TicketDao
import woowacourse.movie.data.ticket.toDomainModel
import woowacourse.movie.data.ticket.toEntity
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

interface TicketRepository {
    fun getAll(): List<Ticket>

    fun save(ticket: Ticket)
}

class LocalTicketRepository(
    private val dao: TicketDao,
) : TicketRepository {
    override fun getAll(): List<Ticket> {
        var result: List<Ticket> = emptyList()
        thread {
            result = dao.getAll().map { it.toDomainModel() }
        }.join()
        return result
    }

    override fun save(ticket: Ticket) {
        thread {
            dao.insert(ticket.toEntity())
        }.join()
    }
}
