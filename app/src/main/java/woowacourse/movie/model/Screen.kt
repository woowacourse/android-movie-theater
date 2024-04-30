package woowacourse.movie.model

import java.time.LocalTime

data class Screen(
    val movieId: Long,
    // val screenPeriod: ScreeningDates,
    val screenSchedule: List<LocalTime>,
)
