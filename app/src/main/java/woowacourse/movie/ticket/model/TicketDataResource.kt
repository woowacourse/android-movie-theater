package woowacourse.movie.ticket.model

import woowacourse.movie.detail.model.Count

object TicketDataResource {
    var ticket: Ticket =
        Ticket(
            -1,
            "",
            "",
            Count(1),
            emptyList(),
            -1,
            0,
        )

    var dbTicket: DbTicket =
        DbTicket(
            "",
            "",
            "",
            0,
            "",
            "",
            0,
        )
}
