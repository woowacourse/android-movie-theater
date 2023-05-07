package woowacourse.movie.domain.model

import java.time.LocalTime

data class TheaterMovie(
    val theaterName: String = "",
    val movie: Movie,
    val screenTimes: List<LocalTime>
)
