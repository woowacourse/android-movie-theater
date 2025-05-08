package woowacourse.movie.domain

import java.io.Serializable

data class Showings(
    val theaterName: String,
    val showings: ScheduleTime,
) : Serializable
