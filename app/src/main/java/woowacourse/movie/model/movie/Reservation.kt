package woowacourse.movie.model.movie

import java.time.LocalDateTime

data class Reservation(
    val title: String,
    val theater: String,
    val screeningStartDateTime: LocalDateTime,
    val reservationCount: ReservationCount,
    val id: Long = 0,
)
