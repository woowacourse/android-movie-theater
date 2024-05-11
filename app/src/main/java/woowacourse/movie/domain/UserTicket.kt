package woowacourse.movie.domain

import java.time.LocalDateTime

data class UserTicket(
    val id: Long? = null,
    val title: String,
    val theater: String,
    val screeningStartDateTime: LocalDateTime,
    val seatInformation: SeatInformation,
)
