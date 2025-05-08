package woowacourse.movie.mapper

import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket

fun Ticket.toEntity(): TicketEntity {
    return TicketEntity(
        title = title,
        date = selectedDate,
        time = selectedTime,
        headCount = headCount.value,
        seat = seats.seats,
        theater = theater,
        price = amount,
    )
}

fun TicketEntity.toDomain(): Ticket {
    return Ticket(
        theater = theater,
        title = title,
        headCount = HeadCount(headCount),
        selectedDate = date,
        selectedTime = time,
        seats = Seats(seat),
    )
}
