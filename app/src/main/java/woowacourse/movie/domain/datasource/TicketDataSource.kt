package woowacourse.movie.domain.datasource

import woowacourse.movie.domain.Callback
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat

interface TicketDataSource {
    fun addTicket(
        booking: Booking,
        seats: Set<Seat>,
        price: Int,
        callback: Callback<Long>,
    )

    fun readAllTicket(callback: Callback<List<Ticket>>)

    fun getTicketById(
        ticketId: Long,
        callback: Callback<Ticket>,
    )
}
