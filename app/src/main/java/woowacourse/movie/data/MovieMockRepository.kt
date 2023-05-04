package woowacourse.movie.data

import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.ScreeningDateTimes
import woowacourse.movie.domain.repository.MovieRepository
import java.time.LocalDate
import java.time.LocalTime

object MovieMockRepository : MovieRepository {

    private val movies = List(1000) {
        listOf(
            Movie(
                "해리 포터와 마법사의 돌",
                ScreeningDateTimes.of(
                    LocalDate.of(2024, 3, 1),
                    LocalDate.of(2024, 3, 31),
                    listOf(LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(16, 0)),
                ),
                Minute(152),
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            ),
            Movie(
                "해리 포터와 비밀의 방",
                ScreeningDateTimes.of(
                    LocalDate.of(2024, 4, 1),
                    LocalDate.of(2024, 4, 28),
                    listOf(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)),
                ),
                Minute(162),
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            ),
            Movie(
                "해리 포터와 아즈카반의 죄수",
                ScreeningDateTimes.of(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 31),
                    listOf(LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(14, 0)),
                ),
                Minute(141),
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            ),
            Movie(
                "해리 포터와 불의 잔",
                ScreeningDateTimes.of(
                    LocalDate.of(2024, 6, 1),
                    LocalDate.of(2024, 6, 28),
                    listOf(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)),
                ),
                Minute(157),
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            ),
        )
    }.flatten()

    override fun findAll(): List<Movie> {
        return movies.toList()
    }
}
