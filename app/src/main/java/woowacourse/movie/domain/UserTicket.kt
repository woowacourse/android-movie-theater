package woowacourse.movie.domain

import woowacourse.movie.data.database.ticket.TicketEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class UserTicket(
    val id: Long? = null,
    val title: String,
    val theater: String,
    val screeningStartDateTime: LocalDateTime,
    val seatInformation: SeatInformation,
)
