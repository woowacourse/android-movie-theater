package woowacourse.movie.fixture

import woowacourse.movie.domain.model.theater.Screening
import java.time.LocalDateTime

val MOVIE_1_SCREENING =
    Screening(
        movieId = 1,
        time = LocalDateTime.of(2025, 4, 29, 9, 30),
    )

val MOVIE_2_SCREENING =
    Screening(
        movieId = 2,
        time = LocalDateTime.of(2025, 4, 29, 9, 30),
    )
