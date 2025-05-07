package woowacourse.movie.domain

import java.io.Serializable

data class Showing(
    val theaterName: String,
    val scheduleTime: ScheduleTime,
) : Serializable
