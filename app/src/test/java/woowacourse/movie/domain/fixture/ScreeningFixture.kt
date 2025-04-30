package woowacourse.movie.domain.fixture

import woowacourse.movie.domain.model.theater.Screening
import java.time.LocalDateTime

val screeningFixtureWithMovieId1 =
    Screening(
        movieId = 1,
        screenTime = LocalDateTime.of(2025, 4, 29, 9, 30),
    )

val screeningFixtureWithMovieId2 =
    Screening(
        movieId = 2,
        screenTime = LocalDateTime.of(2025, 4, 29, 9, 30),
    )
