package woowacourse.movie.data

import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.data.mapper.toEntity
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.TicketRepository

class TicketRepositoryImpl(
    private val dao: TicketDao,
) : TicketRepository {
    override fun getAll(): List<Ticket> {
        return dao.getAll().toDomain()
    }

    override fun insertAll(vararg ticket: Ticket) {
        dao.insertAll(*ticket.map { it.toEntity() }.toTypedArray())
    }
}
