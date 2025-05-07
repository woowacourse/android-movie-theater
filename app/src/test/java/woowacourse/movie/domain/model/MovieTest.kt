package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieTest {
    private val movie = DummyMovie.dummyMovie[0]

    @Test
    fun `Movie의 screening은 자기 자신이 포함된 요소를 반환한다`() {
        assertThat(movie.screening).isEqualTo(
            listOf(
                Screening(
                    listOf(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0)),
                    Cinema(1, "선릉"),
                    DummyMovie.dummyMovie[0],
                ),
                Screening(
                    listOf(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)),
                    Cinema(2, "잠실"),
                    DummyMovie.dummyMovie[0],
                ),
                Screening(
                    listOf(LocalTime.of(13, 0, 0), LocalTime.of(15, 0, 0)),
                    Cinema(3, "강남"),
                    DummyMovie.dummyMovie[0],
                ),
            ),
        )
    }

    @Test
    fun `현재 날짜보다 이후의 상영 시간을 반환한다`() {
        val now = LocalDateTime.of(2025, 5, 29, 9, 0, 0)
        val availableDates = movie.availableDates(now)
        assertThat(availableDates).isEqualTo(
            listOf(
                LocalDate.of(2025, 5, 29),
                LocalDate.of(2025, 5, 30),
            ),
        )
    }
}
