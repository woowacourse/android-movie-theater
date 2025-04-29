package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.movie.Movie
import java.time.LocalTime

data class ScreeningInfo(
    val movie: Movie,
    val times: List<LocalTime>
)
