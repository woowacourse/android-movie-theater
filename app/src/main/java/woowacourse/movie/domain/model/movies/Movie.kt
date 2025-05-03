package woowacourse.movie.domain.model.movies

import java.time.LocalDate

class Movie(
    val id: Int,
    val title: String,
    val posterResource: String,
    val screeningStartDate: LocalDate,
    val screeningEndDate: LocalDate,
    val runningTime: Int,
)
