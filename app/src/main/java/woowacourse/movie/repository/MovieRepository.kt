package woowacourse.movie.repository

import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreeningDates
import java.time.LocalDate
import kotlin.Result

class MovieRepository {
    private val movies =
        List(100) {
            Movie(
                id = it,
                title = "해리 포터 $it",
                thumbnail = R.drawable.movie1,
                screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
                runningTime = 152,
                introduction =
                    """
                    《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.
                    """.trimIndent(),
            )
        }

    private val tmpMovies: List<Movie> =
        listOf(
            Movie(
                id = 1,
                title = "해리 포터와 마법사의 돌",
                runningTime = 152,
                screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
                introduction = "해리포터와 마법사의 돌 영화에 대한 설명입니다",
                thumbnail = R.drawable.harry_potter_1,
            ),
            Movie(
                id = 2,
                title = "해리 포터와 비밀의 방",
                runningTime = 162,
                screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
                introduction = "해리포터와 비밀의 방 영화에 대한 설명입니다",
                thumbnail = R.drawable.harry_potter_2,
            ),
            Movie(
                id = 3,
                title = "해리 포터와 아즈카반의 죄수",
                runningTime = 141,
                screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
                introduction = "해리포터와 아즈카반의 죄수 영화에 대한 설명입니다",
                thumbnail = R.drawable.harry_potter_3,
            ),
            Movie(
                id = 1,
                title = "해리 포터와 마법사의 돌",
                runningTime = 152,
                screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
                introduction = "해리포터와 마법사의 돌 영화에 대한 설명입니다",
                thumbnail = R.drawable.harry_potter_1,
            ),
        )

    fun getAllMovies(): List<Movie> = tmpMovies

    fun findMovieById(id: Int): Result<Movie> {
        val movie = tmpMovies.find { it.id == id }
        return movie?.let { Result.success(it) } ?: Result.failure(Exception("존재하지 않는 아이디 값입니다."))
    }
}
