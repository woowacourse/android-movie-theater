package woowacourse.movie.model.movie

import java.time.LocalTime

data class Theater(
    val id: Long = 0,
    val name: String,
    val screeningTimes: List<LocalTime>,
)
