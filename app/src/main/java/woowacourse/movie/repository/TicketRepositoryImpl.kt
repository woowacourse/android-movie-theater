package woowacourse.movie.repository

import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Ticket

class TicketRepositoryImpl(val ticketDao: TicketDao) : Repository<Ticket> {
    override fun findAll(): List<Ticket>? {
        return runCatching {
            ticketDao.findAll().map {
                entityToDomain(it)
            }
        }.getOrNull()
    }

    private fun entityToDomain(entity: TicketEntity): Ticket {
        return Ticket(
            entity.title,
            entity.showTime,
            entity.seats,
            entity.reservationCount,
            Cinema(1, entity.cinemaName),
        )
    }
}
