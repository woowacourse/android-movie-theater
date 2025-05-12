package woowacourse.movie.repository

import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.mapper.toEntity
import woowacourse.movie.repository.mapper.toTicket

class TicketRepository(
    val ticketDao: TicketDao,
) {
    fun findAll(): Result<List<Ticket>> {
        return runCatching {
            ticketDao.findAll().map {
                it.toTicket()
            }
        }
    }

    fun save(ticket: Ticket): Result<Unit> {
        return runCatching {
            ticketDao.save(
                ticket.toEntity(),
                ticket.seats.map {
                    SeatEntity(
                        row = it.row,
                        column = it.column,
                    )
                },
            )
        }
    }
}
