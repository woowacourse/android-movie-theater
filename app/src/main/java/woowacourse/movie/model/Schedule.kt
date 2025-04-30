package woowacourse.movie.model

import java.time.LocalTime

class Schedule(
    val movie: Movie,
    val screeningTimes: List<LocalTime>,
)
