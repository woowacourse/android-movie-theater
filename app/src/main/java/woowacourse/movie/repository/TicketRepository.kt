package woowacourse.movie.repository

import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Ticket

class TicketRepository(val ticketDao: TicketDao) : Repository<Ticket> {
    override fun findAll(): Result<List<Ticket>> {
        return runCatching {
            ticketDao.findAll().map {
                it.toTicket()
            }
        }
    }

    override fun save(ticket: Ticket): Result<Unit> {
        return runCatching {
            ticketDao.save(ticket.toEntity())
        }
    }

    private fun TicketEntity.toTicket(): Ticket {
        return Ticket(
            title,
            showTime,
            seats,
            reservationCount,
            Cinema(1, cinemaName),
        )
    }

    private fun Ticket.toEntity(): TicketEntity {
        return TicketEntity(
            title = title,
            showTime = showTime,
            reservationCount = reservationCount,
            cinemaName = cinema.name,
            seats = seats,
            totalPrice = totalPrice(),
        )
    }
}
