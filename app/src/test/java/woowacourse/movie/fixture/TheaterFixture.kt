package woowacourse.movie.fixture

import woowacourse.movie.domain.model.theater.Screening
import woowacourse.movie.domain.model.theater.Theater
import java.time.LocalDateTime

val THEATER =
    Theater(
        "선릉 극장",
        listOf(Screening(0, LocalDateTime.of(2025, 5, 11, 12, 0))),
    )
