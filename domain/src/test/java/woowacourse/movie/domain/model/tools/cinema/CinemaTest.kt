package woowacourse.movie.domain.model.tools.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime

class CinemaTest {
    @Test
    fun `시네마 이름으로 상영 시간을 찾을 수 있다`() {
        // given
        val cinema = Cinema.of(
            "선릉" to MovieTimes.of(1L, 9, 15, 2),
            "잠실" to MovieTimes.of(2L, 14, 18, 1),
        )

        // when
        val actual = cinema.findMovieTimes("선릉")
        val expected =
            MovieTimes(
                1L,
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
