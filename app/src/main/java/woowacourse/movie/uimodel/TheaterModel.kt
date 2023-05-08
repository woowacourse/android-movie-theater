package woowacourse.movie.uimodel

import java.time.LocalDateTime

data class TheaterModel(
    val name: String,
    val size: Int,
    val times: List<LocalDateTime>,
)
