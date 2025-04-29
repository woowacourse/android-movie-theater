package woowacourse.movie.domain

import java.time.LocalDateTime

data class ScheduleTime(
    val time: List<LocalDateTime>
) {
    fun afterCurrentTimeSchedule(currentTime: LocalDateTime): List<LocalDateTime> {
        return time.filter { it > currentTime }
    }
}
