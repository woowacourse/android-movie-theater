package woowacourse.movie.fixture

import woowacourse.movie.domain.model.Movie
import java.time.LocalDate

val HARRY_POTTER =
    Movie(
        title = "해리 포터와 마법사의 돌",
        startDate = LocalDate.of(2025, 4, 1),
        endDate = LocalDate.of(2025, 5, 30),
        runningTime = 152,
    )
