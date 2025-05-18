package woowacourse.movie.mapper

import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket

fun Ticket.toEntity(): TicketEntity {
    return TicketEntity(
        title = title,
        date = selectedDate,
        time = selectedTime,
        headCount = headCount.value,
        seat = fromSeats(seats),
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
        seats = toSeats(seat),
    )
}

private fun fromSeats(seats: Seats): String {
    return seats.seats.map { it.toUiModel() }.joinToString(", ") { point ->
        "${'A' + point.row}${point.col + 1}"
    }
}

private fun toSeats(seats: String): Seats {
    return Seats(
        seats.split(", ")
            .map { seat ->
                val row = seat[0] - 'A'
                val col = seat.substring(1).toInt() - 1
                Seat(row, col)
            }.toSet(),
    )
}
