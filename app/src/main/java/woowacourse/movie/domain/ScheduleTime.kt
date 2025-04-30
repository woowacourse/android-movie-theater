package woowacourse.movie.domain

import java.io.Serializable
import java.time.LocalDateTime

data class ScheduleTime(
    val times: List<LocalDateTime>,
) : Serializable {
    fun afterCurrentTimeSchedule(currentTime: LocalDateTime): List<LocalDateTime> {
        return times.filter { it > currentTime }
    }
}
