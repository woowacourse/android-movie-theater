package woowacourse.movie.data

import woowacourse.movie.domain.fixture.ticketFixtures
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.datasource.TicketDataSource
import woowacourse.movie.domain.model.seat.Seat

class FakeTicketDataSource : TicketDataSource {
    private val tickets = mutableMapOf<Long, Ticket>()

    init {
        ticketFixtures.forEach {
            addTicket(
                booking =
                    Booking(
                        movieTitle = it.title,
                        bookingDate = it.bookingDate,
                        bookingTime = it.bookingTime,
                        theaterName = it.theaterName,
                        count = it.count,
                    ),
                seats = it.seats,
                price = it.price,
            )
        }
    }

    override fun addTicket(
        booking: Booking,
        seats: Set<Seat>,
        price: Int,
    ): Long {
        val id = (tickets.keys.maxOrNull() ?: 0L) + 1L
        val ticket =
            Ticket(
                id = id,
                title = booking.movieTitle,
                bookingDate = booking.bookingDate,
                bookingTime = booking.bookingTime,
                theaterName = booking.theaterName,
                count = booking.count,
                price = price,
                seats = seats,
            )
        tickets[id] = ticket
        return id
    }

    override fun readAllTicket(): List<Ticket> = tickets.values.toList()

    override fun getTicketById(ticketId: Long): Ticket = tickets[ticketId]!!
}
