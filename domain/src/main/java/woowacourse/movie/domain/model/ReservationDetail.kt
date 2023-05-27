package woowacourse.movie.domain.model

import java.time.LocalDateTime

data class ReservationDetail(
    val date: LocalDateTime,
    val peopleCount: Int,
    val theaterName: String
)
