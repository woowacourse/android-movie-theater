package woowacourse.movie.domain.model.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDateTime

class TheatersTest {
    @Test
    fun `모든 극장이 결과에 포함되며, 해당 영화가 있는 극장만 시간 목록을 포함한다`() {
        val now = LocalDateTime.now()

        val theaterWithMovie =
            Theater(
                name = "강남 극장",
                schedules =
                    listOf(
                        MovieSchedule(
                            movieId = 1,
                            times = listOf(now.plusHours(1), now.plusHours(2)),
                        ),
                    ),
            )

        val theaterWithoutMovie =
            Theater(
                name = "신촌 극장",
                schedules =
                    listOf(
                        MovieSchedule(
                            movieId = 2,
                            times = listOf(now.plusHours(1)),
                        ),
                    ),
            )

        val theaters = Theaters(listOf(theaterWithMovie, theaterWithoutMovie))
        val result = theaters.findTheatersByMovieId(movieId = 1, now = now)

        assertAll(
            { assertThat(result.keys).containsExactlyInAnyOrder("강남 극장", "신촌 극장") },
            { assertThat(result["강남 극장"]).containsExactly(now.plusHours(1), now.plusHours(2)) },
            { assertThat(result["신촌 극장"]).isEmpty() },
        )
    }
}
