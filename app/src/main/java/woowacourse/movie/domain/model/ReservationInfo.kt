package woowacourse.movie.domain.model

import java.time.LocalDateTime

data class ReservationInfo(
    val title: String,
    val dateTime: LocalDateTime,
    val seats: Seats,
    val count: TicketCount,
)
