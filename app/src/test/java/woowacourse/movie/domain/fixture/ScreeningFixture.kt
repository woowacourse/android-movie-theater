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

val screeningFixtureWithMovieId3 =
    Screening(
        movieId = 3,
        screenTime = LocalDateTime.of(2025, 4, 25, 9, 30),
    )

val screeningFixtureWithMovieId4 =
    Screening(
        movieId = 4,
        screenTime = LocalDateTime.of(2025, 4, 25, 9, 30),
    )

val screeningFixtureWithMovieId5 =
    Screening(
        movieId = 5,
        screenTime = LocalDateTime.of(2025, 4, 25, 9, 30),
    )
