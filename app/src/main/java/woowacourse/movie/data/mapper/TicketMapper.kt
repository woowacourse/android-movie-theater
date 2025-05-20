package woowacourse.movie.data.mapper

import woowacourse.movie.data.TicketEntity
import woowacourse.movie.domain.Ticket
import woowacourse.movie.view.reservation.TicketUi

fun List<TicketEntity>.toDomain() =
    this.map { entity ->
        Ticket(
            entity.title,
            entity.date,
            entity.personnel,
            entity.theaterName,
            entity.seats,
        )
    }

fun Ticket.toEntity() =
    TicketEntity(
        title = this.title,
        date = this.date,
        personnel = this.personnel,
        theaterName = this.theaterName,
        seats = this.seats,
    )

fun TicketUi.toDomain() =
    Ticket(
        title = this.title,
        date = this.date,
        personnel = this.personnel,
        theaterName = this.theaterName,
        seats = this.seats,
    )

fun Ticket.toUi() =
    TicketUi(
        title = this.title,
        date = this.date,
        personnel = this.personnel,
        theaterName = this.theaterName,
        seats = this.seats,
    )
