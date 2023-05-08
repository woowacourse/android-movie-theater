package woowacourse.domain.theater

import woowacourse.domain.movie.Movie
import java.time.LocalTime

data class ScreeningMovie(
    val movie: Movie,
    val times: List<LocalTime>,
)
