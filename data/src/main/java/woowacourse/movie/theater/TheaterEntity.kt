package woowacourse.movie.theater

import java.time.LocalTime

data class TheaterEntity(
    val id: Long,
    val name: String,
    val rowSize: Int,
    val columnSize: Int,
    val screeningTimes: List<LocalTime>
)
