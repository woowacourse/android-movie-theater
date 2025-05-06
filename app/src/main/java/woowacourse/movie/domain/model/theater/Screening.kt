package woowacourse.movie.domain.model.theater

import java.time.LocalDateTime

data class Screening(
    val movieId: Int,
    val time: LocalDateTime,
)
