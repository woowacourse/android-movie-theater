package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TheaterTest {
    @Test
    internal fun 가장_빠른_영화_상영일을_구할_수_있다() {
        // given
        val theater = Theater(
            "선릉", listOf(
                LocalDateTime.of(2023, 5, 1, 0, 0),
                LocalDateTime.of(2023, 5, 2, 0, 0),
                LocalDateTime.of(2023, 5, 3, 0, 0)
            )
        )

        // when
        val actual = theater.startDate
        // then
        val expected = LocalDate.of(2023, 5, 1)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun 가장_늦은_영화_상영일을_구할_수_있다() {
        // given
        val theater = Theater(
            "선릉", listOf(
                LocalDateTime.of(2023, 5, 1, 0, 0),
                LocalDateTime.of(2023, 5, 2, 0, 0),
                LocalDateTime.of(2023, 5, 3, 0, 0)
            )
        )

        // when
        val actual = theater.endDate
        // then
        val expected = LocalDate.of(2023, 5, 3)
        assertThat(actual).isEqualTo(expected)
    }
}
