package woowacourse.movie.data.mapper

import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.model.ticketing.Ticket

fun ReservationEntity.toTicket(): Ticket =
    Ticket(title, theaterName, dateTime, convertedSeats.convertToSeats(), totalPrice)

fun Ticket.toEntity(): ReservationEntity =
    ReservationEntity(
        id = generateUniqueId(),
        title = title,
        theaterName = theaterName,
        dateTime = reservationDateTime,
        convertedSeats = seats.convertToString(),
        reservationCount = count,
        totalPrice = price,
    )

fun Ticket.generateUniqueId(): Long {
    val rawId = "$title-$theaterName-$reservationDateTime-${seats.joinToString()}"
    return rawId.hashCode().toLong()
}
