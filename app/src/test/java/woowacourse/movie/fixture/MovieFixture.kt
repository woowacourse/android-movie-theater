package woowacourse.movie.fixture

import woowacourse.movie.model.Movie
import java.time.LocalDate

const val HARRY_POTTER = "해리포터와 마법사의 돌"
const val STAR_IS_BORN = "스타 이즈 본"

fun createMovie(name: String): Movie =
    Movie(
        title = name,
        imageSource = "harry_potter.png",
        screeningStartDate = LocalDate.of(2025, 5, 10),
        screeningEndDate = LocalDate.of(2025, 5, 15),
        runningTime = 152,
    )
