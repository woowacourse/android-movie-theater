package woowacourse.movie.model

import java.time.LocalTime

data class ScreeningMovie(
    val movieId: Long,
    val screenSchedule: List<LocalTime>,
)
