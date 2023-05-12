package woowacourse.movie.domain

import java.time.LocalTime

data class Theater(
    val name: String,
    val screeningTimes: List<LocalTime>
)
