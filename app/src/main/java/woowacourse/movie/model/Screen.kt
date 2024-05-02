package woowacourse.movie.model

import java.time.LocalTime

data class Screen(
    val movieId: Long,
    val screenSchedule: List<LocalTime>,
)
