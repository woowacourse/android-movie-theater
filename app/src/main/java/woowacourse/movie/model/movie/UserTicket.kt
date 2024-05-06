package woowacourse.movie.model.movie

import java.time.LocalDateTime

data class UserTicket(
    val id: Long = 0,
    val title: String,
    val theater: String,
    val screeningStartDateTime: LocalDateTime,
    val reservationDetail: ReservationDetail,
)
