package woowacourse.movie.model.movie

import java.time.LocalTime

class ScreeningMovie(
    val movieContent: MovieContent,
    val screeningTimes: List<LocalTime>
)
