package woowacourse.movie.data.repository

import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.toDomainModel
import woowacourse.movie.data.entity.toEntity
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

interface TicketRepository {
    fun getAll(): List<Ticket>

    fun save(ticket: Ticket)
}

class LocalTicketRepository(
    private val database: MovieDatabase,
) : TicketRepository {
    override fun getAll(): List<Ticket> {
        var result: List<Ticket> = emptyList()
        thread {
            result = database.ticketDao.getAll().map { it.toDomainModel() }
        }.join()
        return result
    }

    override fun save(ticket: Ticket) {
        thread {
            database.ticketDao.insert(ticket.toEntity())
        }.join()
    }
}
