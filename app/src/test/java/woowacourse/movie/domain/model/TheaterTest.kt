package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class TheaterTest {
    @Test
    fun `극장은 영화와 날짜, 시간으로 상영 가능한 극장을 반환한다`() {
        val movie = TEST_DUMMY_MOVIES.first()

        val theater =
            Theater(
                name = "선릉 극장",
                movieSchedules = listOf(TEST_MOVIE_SCHEDULES[0], TEST_MOVIE_SCHEDULES[1]),
            )
        val date = LocalDate.of(2025, 4, 14)
        val time = LocalTime.of(10, 0)
        val expected = Theater("선릉 극장", listOf(TEST_MOVIE_SCHEDULES[1]))

        val actual: Theater = theater.theaterByMovie(movie, date, time)

        assertThat(actual).isEqualTo(expected)
    }
}
