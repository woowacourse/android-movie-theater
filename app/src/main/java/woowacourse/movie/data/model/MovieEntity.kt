package woowacourse.movie.data.model

import java.time.LocalDate

data class MovieEntity(
    val poster: Int,
    val title: String,
    val releaseDate: String,
    val runningTime: String,
    val synopsis: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val movieSchedule: List<String> = emptyList(),
)