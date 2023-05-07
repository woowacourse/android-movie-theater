package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TheatersTest {
    private fun Theater(name: String, day: Int): Theater {
        return Theater(name, listOf(LocalDateTime.of(2023, 5, day, 0, 0)))
    }

    @Test
    internal fun `가장 빠른 상영일을 구할 수 있다`() {
        // given
        val theaters = Theaters(
            listOf(
                Theater("선릉", 3),
                Theater("잠실", 4),
                Theater("강남", 5)
            )
        )
        // when
        val actual = theaters.getStartDate()
        // then
        val expected = LocalDate.of(2023, 5, 3)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `가장 늦은 상영일을 구할 수 있다`() {
        // given
        val theaters = Theaters(
            listOf(
                Theater("선릉", 3),
                Theater("잠실", 4),
                Theater("강남", 5)
            )
        )
        // when
        val actual = theaters.getEndDate()
        // then
        val expected = LocalDate.of(2023, 5, 5)
        assertThat(actual).isEqualTo(expected)
    }
}
