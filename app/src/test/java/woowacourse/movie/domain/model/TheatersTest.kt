package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.model.ScreenData
import java.time.LocalDate

class TheatersTest {
    @Test
    fun `영화를 상영하고 있는 극장을 불러온다`() {
        val theater1 =
            Theater(
                1,
                "선릉",
                listOf(
                    fakeScreenWithScreenAndMovieId(screenId = 1, movieId = 1),
                    fakeScreenWithScreenAndMovieId(screenId = 2, movieId = 2),
                    fakeScreenWithScreenAndMovieId(screenId = 3, movieId = 3),
                ),
            )

        val theater2 =
            Theater(
                2,
                "강남",
                listOf(
                    fakeScreenWithScreenAndMovieId(screenId = 4, movieId = 1),
                    fakeScreenWithScreenAndMovieId(screenId = 5, movieId = 2),
                    fakeScreenWithScreenAndMovieId(screenId = 6, movieId = 4),
                ),
            )

        val theater3 =
            Theater(
                2,
                "역삼",
                listOf(
                    fakeScreenWithScreenAndMovieId(screenId = 7, movieId = 4),
                    fakeScreenWithScreenAndMovieId(screenId = 8, movieId = 5),
                    fakeScreenWithScreenAndMovieId(screenId = 9, movieId = 6),
                ),
            )

        val theaters =
            Theaters(
                theater1,
                theater2,
                theater3,
            )

        val actual = theaters.screeningTheater(fakeMovieWithMovieId(1))
        val expected = Theaters(theater1, theater2)
        assertThat(actual).isEqualTo(expected)
    }

    private fun fakeScreenWithScreenAndMovieId(
        screenId: Int,
        movieId: Int,
    ): ScreenData =
        ScreenData(
            id = screenId,
            movie = fakeMovieWithMovieId(movieId),
            dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
        )

    private fun fakeMovieWithMovieId(movieId: Int): Movie =
        Movie(
            id = movieId,
            title = "title$movieId",
            runningTime = 100,
            description = "description$movieId",
        )
}
