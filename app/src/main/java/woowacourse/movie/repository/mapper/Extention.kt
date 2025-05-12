package woowacourse.movie.repository.mapper

import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.data.entity.WholeTicketEntity
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Ticket

fun WholeTicketEntity.toTicket(): Ticket {
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

fun Ticket.toEntity(): TicketEntity {
    return TicketEntity(
        title = title,
        showTime = showTime,
        reservationCount = reservationCount,
        cinemaName = cinema.name,
        price = totalPrice(),
    )
}
