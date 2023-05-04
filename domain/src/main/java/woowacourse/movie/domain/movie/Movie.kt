package woowacourse.movie.domain.movie

import java.time.LocalDate

data class Movie(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Minute,
    val summary: String,
    val schedule: Schedule
)
