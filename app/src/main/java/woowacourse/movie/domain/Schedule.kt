package woowacourse.movie.domain

import java.io.Serializable

data class Schedule(
    val movieTitle: String,
    val scheduleTime: ScheduleTime,
) : Serializable
