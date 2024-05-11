package woowacourse.movie.domain.mapper

import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.SeatInformation
import woowacourse.movie.domain.SeatRow
import woowacourse.movie.domain.UserTicket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun UserTicket.toTicketEntity(): TicketEntity =
    TicketEntity(
        title = title,
        theater = theater,
        screeningStartDateTime = screeningStartDateTime.format(DateTimeFormatter.ofPattern("yyyy.M.d HH:mm")),
        reservationCount = seatInformation.reservationCount,
        seats = seatInformation.selectedSeat.joinToString(),
        price = seatInformation.totalSeatAmount(),
    )

fun TicketEntity.toUserTicket(): UserTicket {
    val seat =
        if (seats.isEmpty()) {
            mutableListOf()
        } else {
            seats.split(", ").map {
                Seat(SeatRow.findRow(it[0].code - 65), it[1].digitToInt())
            }.toMutableList()
        }
    return UserTicket(
        id = id,
        title = title,
        theater = theater,
        screeningStartDateTime = LocalDateTime.parse(screeningStartDateTime, DateTimeFormatter.ofPattern("yyyy.M.d HH:mm")),
        seatInformation =
            SeatInformation(
                reservationCount,
                seat,
            ),
    )
}
