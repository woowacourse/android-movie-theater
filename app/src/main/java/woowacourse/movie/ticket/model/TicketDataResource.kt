package woowacourse.movie.ticket.model

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

object TicketDataResource {
    var theaterId: Long = -1

    var movieId: Long = -1

    var price: Int = -1

    var seats = listOf<Seat>()

    var ticketCount: Count = Count(1)

    var screeningDate = ""

    var screeningTime = ""

    var ticket: Ticket =
        Ticket(
            0,
            listOf(),
            Count(1),
            "최초값",
            "최초값",
            -1,
            -1,
        )
}
