package woowacourse.movie.data.datasource

import woowacourse.movie.data.db.TicketDao
import woowacourse.movie.data.db.TicketEntity
import woowacourse.movie.domain.Callback
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.execute
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

class TicketDataSourceImpl(private val dao: TicketDao) : TicketDataSource {
    override fun addTicket(
        booking: Booking,
        seats: Set<Seat>,
        price: Int,
    ): Long {
        val entity =
            TicketEntity(
                movieTitle = booking.movieTitle,
                bookingDateTime = LocalDateTime.of(booking.bookingDate, booking.bookingTime),
                theaterName = booking.theaterName,
                ticketCount = booking.count.value,
                ticketPrice = price,
                seats = seats,
            )

        return dao.insert(entity)
    }

    override fun readAllTicket(): List<Ticket> {
        val result = dao.readAll().map { it.toDomain() }
        return result
    }

    override fun getTicketById(
        ticketId: Long,
        callback: Callback<Ticket>,
    ) = execute(
        task = { dao.readById(ticketId).toDomain() },
        callback = callback,
    )
}
