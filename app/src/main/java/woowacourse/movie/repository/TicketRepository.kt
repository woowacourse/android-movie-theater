package woowacourse.movie.repository

import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.data.entity.WholeTicketEntity
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Ticket

class TicketRepository(
    val ticketDao: TicketDao,
) : Repository<Ticket> {
    override fun findAll(): Result<List<Ticket>> {
        return runCatching {
            ticketDao.findAll().map {
                it.toTicket()
            }
        }
    }

    override fun save(ticket: Ticket): Result<Unit> {
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

    private fun WholeTicketEntity.toTicket(): Ticket {
        return Ticket(
            ticket.title,
            ticket.showTime,
            seats.map {
                Seat(
                    it.row,
                    it.column,
                )
            },
            ticket.reservationCount,
            DummyCinema.dummyCinemas.find { it.name == ticket.cinemaName }!!,
        )
    }

    private fun Ticket.toEntity(): TicketEntity {
        return TicketEntity(
            title = title,
            showTime = showTime,
            reservationCount = reservationCount,
            cinemaName = cinema.name,
            price = totalPrice(),
        )
    }
}
