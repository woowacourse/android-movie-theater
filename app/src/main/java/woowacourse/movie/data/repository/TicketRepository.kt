package woowacourse.movie.data.repository

import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.toDomainModel
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

interface TicketRepository {
    fun getTickets(): List<Ticket>
}

class DefaultTicketRepository(
    private val database: MovieDatabase,
) : TicketRepository {
    override fun getTickets(): List<Ticket> {
        var result: List<Ticket> = emptyList()
        thread {
            result = database.ticketDao.getAll().map { it.toDomainModel() }
        }.join()
        return result
    }
}
