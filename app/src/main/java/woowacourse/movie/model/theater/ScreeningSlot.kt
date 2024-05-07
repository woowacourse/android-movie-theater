package woowacourse.movie.model.theater

import java.time.LocalTime

data class ScreeningSlot(
    val screeningTime: LocalTime,
    val movieId: Int,
)
