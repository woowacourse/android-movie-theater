package woowacourse.movie.domain

import java.io.Serializable
import java.time.LocalTime

data class ScheduleTime(
    val times: List<LocalTime>,
) : Serializable {
    fun afterCurrentTimeSchedule(currentTime: LocalTime): List<LocalTime> {
        return times.filter { it > currentTime }
    }
}
