package woowacourse.movie.domain

import java.time.LocalDateTime

data class ScheduleTime(
    val time: List<LocalDateTime>,
    val currentTime: LocalDateTime,
) {
    val afterCurrentTimeScheduleCount get() = afterCurrentTimeSchedule().size

    fun afterCurrentTimeSchedule(): List<LocalDateTime>  {
        return time.filter { it > currentTime }
    }
}
