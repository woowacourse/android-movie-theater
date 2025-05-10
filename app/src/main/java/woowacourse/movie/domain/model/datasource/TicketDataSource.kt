package woowacourse.movie.domain.model.datasource

import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat

interface TicketDataSource {
    fun addTicket(
        booking: Booking,
        seats: Set<Seat>,
        price: Int,
    ): Long

    fun readAllTicket(): List<Ticket>

    fun getTicketById(ticketId: Long): Ticket
}
