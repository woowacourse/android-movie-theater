package woowacourse.movie.data.db

import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

class TicketDataSource(private val dao: TicketDao) {
    fun addTicket(
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

    fun readAllTicket(): List<Ticket> {
        val result = dao.readAll().map { it.toDomain() }
        return result
    }

    fun getTicketById(ticketId: Long) = dao.readById(ticketId).toDomain()
}
