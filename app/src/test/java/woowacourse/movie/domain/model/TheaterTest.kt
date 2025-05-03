package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime

class TheaterTest {
    @Test
    fun `극장은 영화와 날짜, 시간으로 상영 가능한 극장을 반환한다`() {
        val movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE

        val theater =
            Theater(
                name = "선릉 극장",
                schedules =
                    listOf(
                        SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_10_1300,
                        SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_15_1630,
                    ),
            )
        val date = LocalDate.of(2025, 4, 14)
        val time = LocalTime.of(10, 0)
        val expected = Theater("선릉 극장", listOf(SCHEDULE_HARRY_PHILOSOPHERS_STONE_2025_04_15_1630))

        val actual: Theater = theater.theaterByMovie(movie, date, time)

        assertThat(actual).isEqualTo(expected)
    }
}
