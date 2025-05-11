package woowacourse.movie.fixture

import woowacourse.movie.model.Movie
import java.time.LocalDate

const val HARRY_POTTER = "해리 포터와 마법사의 돌"

fun createMovie(name: String): Movie {
    return Movie(
        title = name,
        screeningStartDate = LocalDate.of(2028, 10, 11),
        screeningEndDate = LocalDate.of(2028, 10, 25),
        runningTime = 152,
    )
}
