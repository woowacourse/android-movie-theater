package woowacourse.movie.domain

import java.io.Serializable

data class Schedule(
    val movie: Movie,
    val scheduleTime: ScheduleTime,
) : Serializable
