package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.ScheduleTime
import java.io.Serializable
import java.time.LocalTime

data class Showing(
    val theaterName: String,
    val scheduleTime: ScheduleTime,
) : Serializable {
    val remainingScheduleCount: Int
        get() = scheduleTime.afterCurrentTimeSchedule(LocalTime.now()).size
}
