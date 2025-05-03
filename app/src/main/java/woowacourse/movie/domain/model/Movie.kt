package woowacourse.movie.domain.model

import java.time.LocalDate

data class Movie(
    val title: String,
    val poster: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
)
