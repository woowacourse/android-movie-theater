package woowacourse.movie.model

import java.time.LocalTime

class ScreeningInfo(
    val movie: Movie,
    val screeningTimes: List<LocalTime>,
)
