package woowacourse.movie.domain

import java.time.LocalTime

data class Theater(
    val id: Long = 0,
    val name: String,
    val screeningTimes: List<LocalTime>,
)
