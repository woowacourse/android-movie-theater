package woowacourse.movie.model.ui

import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketDao

class FakeUserTicketDao : UserTicketDao {
    private var id: Long = 0
    private val userTickets = mutableMapOf<Long, UserTicket>()

    override fun find(id: Long): UserTicket {
        return userTickets[id]
            ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<UserTicket> {
        return userTickets.map { it.value }
    }

    override fun insert(userTicket: UserTicket): Long {
        userTickets[id] = userTicket.copy(id = id)
        return id++
    }

    override fun delete(userTicket: UserTicket) {
        userTickets.remove(userTicket.id)
    }

    override fun deleteAll() {
        userTickets.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)

    companion object {
        private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    }
}
