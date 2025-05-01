package woowacourse.movie.fixture

import woowacourse.movie.model.Movie
import java.time.LocalDate

const val HARRY_POTTER = "해리 포터와 마법사의 돌"
const val HARRY_POTTER_SECRET = "해리 포터와 비밀의 방"
const val HARRY_POTTER_CONVICT = "해리 포터와 아즈카반의 죄수"
const val HARRY_POTTER_FIRE = "해리 포터와 불의 잔"
const val STAR_IS_BORN = "스타 이즈 본"

fun createMovie(name: String): Movie {
    return Movie(
        imageSource = "harry_potter.png",
        title = name,
        screeningStartDate = LocalDate.of(2028, 10, 11),
        screeningEndDate = LocalDate.of(2028, 10, 25),
        runningTime = 152,
    )
}

fun createMovieList(): List<Movie> {
    return listOf(
        createMovie(HARRY_POTTER),
        createMovie(HARRY_POTTER_SECRET),
        createMovie(HARRY_POTTER_CONVICT),
        createMovie(HARRY_POTTER_FIRE),
        createMovie(STAR_IS_BORN),
    )
}
