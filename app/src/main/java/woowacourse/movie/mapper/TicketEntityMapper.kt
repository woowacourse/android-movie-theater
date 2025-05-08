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
        seat = convertSeat(seats.seats),
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
        seats = Seats(parseSeats(seat)),
    )
}

private fun convertSeat(seats: Set<Seat>): String {
    return seats.joinToString(", ") { point ->
        "${'A' + point.row}${point.col + 1}"
    }
}

private fun parseSeats(seatString: String): Set<Seat> {
    return seatString.split(", ")
        .map { seat ->
            val rowChar = seat[0]
            val colNumber = seat.substring(1)
            val row = rowChar - 'A'
            val col = colNumber.toInt() - 1
            Seat(row, col)
        }.toSet()
}
