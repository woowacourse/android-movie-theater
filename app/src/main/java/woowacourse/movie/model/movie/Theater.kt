package woowacourse.movie.model.movie

import java.time.LocalTime

data class Theater(
    val name: String,
    val screeningTimes: List<LocalTime>,
    val id: Long = 0,
)
