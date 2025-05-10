package woowacourse.movie.data

import woowacourse.movie.domain.fixture.ticketFixture
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.datasource.TicketDataSource
import woowacourse.movie.domain.model.seat.Seat

class FakeTicketDataSource : TicketDataSource {
    private val tickets = mutableMapOf<Long, Ticket>()

    init {
        addTicket(
            booking =
                Booking(
                    movieTitle = ticketFixture.title,
                    bookingDate = ticketFixture.bookingDate,
                    bookingTime = ticketFixture.bookingTime,
                    theaterName = ticketFixture.theaterName,
                    count = ticketFixture.count,
                ),
            seats = ticketFixture.seats,
            price = ticketFixture.price,
        )
    }

    override fun addTicket(
        booking: Booking,
        seats: Set<Seat>,
        price: Int,
    ): Long {
        val id = (tickets.keys.maxOrNull() ?: 0L) + 1L
        tickets[id] = ticketFixture

        return id
    }

    override fun readAllTicket(): List<Ticket> = tickets.values.toList()

    override fun getTicketById(ticketId: Long): Ticket = tickets[ticketId]!!
}
