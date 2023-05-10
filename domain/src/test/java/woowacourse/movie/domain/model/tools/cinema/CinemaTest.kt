package woowacourse.movie.domain.model.tools.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime

class CinemaTest {
    @Test
    fun `시네마 이름으로 상영 시간을 찾을 수 있다`() {
        // given
        val cinema = Cinema.of(
            "선릉",
            1L to MovieTimes.of(9, 15, 2),
        )

        // when
        val actual = cinema.findMovieTimes(1L)
        val expected =
            MovieTimes(
                listOf(
                    LocalTime.of(9, 0),
                    LocalTime.of(11, 0),
                    LocalTime.of(13, 0),
                    LocalTime.of(15, 0),
                ),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
