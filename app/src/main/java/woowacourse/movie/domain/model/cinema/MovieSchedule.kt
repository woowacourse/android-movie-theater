package woowacourse.movie.domain.model.cinema

import java.time.LocalDateTime

data class MovieSchedule(
    val movieId: Int,
    val times: List<LocalDateTime>,
) {
    fun getTimesAfter(time: LocalDateTime): List<LocalDateTime> = times.filter { it.isAfter(time) }
}
