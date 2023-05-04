package woowacourse.movie.domain

import java.time.LocalTime

data class MovieSchedule(
    val movie: Movie,
    val times: List<LocalTime>
)
