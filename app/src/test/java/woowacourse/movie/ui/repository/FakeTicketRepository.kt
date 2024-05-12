package woowacourse.movie.ui.repository

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity

object FakeTicketRepository : TicketDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val tickets = mutableMapOf<Long, TicketEntity>()

    override fun save(data: TicketEntity): Long {
        tickets[id] = data.copy(id = id)
        return id++
    }

    override fun find(id: Long): TicketEntity {
        return tickets[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<TicketEntity> {
        return tickets.map { it.value }
    }

    override fun deleteAll() {
        tickets.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
