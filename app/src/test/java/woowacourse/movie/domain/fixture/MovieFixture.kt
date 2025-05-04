package woowacourse.movie.domain.fixture

import woowacourse.movie.domain.model.booking.ScreeningDates
import woowacourse.movie.domain.model.movies.Movie
import java.time.LocalDate

val screeningDatesFixture =
    ScreeningDates(
        listOf(
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 2),
            LocalDate.of(2025, 5, 3),
            LocalDate.of(2025, 5, 4),
        ),
    )
val harryPotter1MoviesFixture =
    Movie(
        0,
        "해리 포터와 마법사의 돌",
        "harry_potter_1",
        ScreeningDates(
            listOf(
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 2),
                LocalDate.of(2025, 5, 3),
            ),
        ),
        152,
    )
