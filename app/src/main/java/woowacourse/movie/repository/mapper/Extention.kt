package woowacourse.movie.repository.mapper

import woowacourse.movie.data.entity.CinemaEntity
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.data.entity.WholeTicketEntity
import woowacourse.movie.domain.model.Cinema
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
        cinema.toCinema(),
    )
}

fun Ticket.toEntity(): TicketEntity {
    return TicketEntity(
        title = title,
        showTime = showTime,
        reservationCount = reservationCount,
        cinemaId = cinema.id,
        price = totalPrice(),
    )
}

fun Cinema.toEntity(): CinemaEntity {
    return CinemaEntity(
        cinemaName = name,
    )
}

fun CinemaEntity.toCinema(): Cinema {
    return Cinema(
        id = id,
        name = cinemaName,
    )
}
