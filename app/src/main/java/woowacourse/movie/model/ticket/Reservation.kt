package woowacourse.movie.model.ticket

import java.time.LocalDateTime

data class Reservation(
    val movieId: Int,
    val theaterId: Int,
    val headCount: HeadCount? = null,
    val screeningDateTime: LocalDateTime? = null,
)
