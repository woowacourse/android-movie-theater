package woowacourse.movie.domain.model.movie

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class ScreeningPeriodTest {
    private lateinit var screeningPeriod: ScreeningPeriod

    @BeforeEach
    fun setUp() {
        screeningPeriod = ScreeningPeriod(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 3))
    }

    @Test
    fun `상영 기간은 지정한 시작 날짜와 종료 날짜로 생성된다`() {
        val startDate = LocalDate.of(2025, 4, 1)
        val endDate = LocalDate.of(2025, 4, 3)
        val screeningPeriod = ScreeningPeriod(startDate, endDate)

        val startDateExpected = LocalDate.of(2025, 4, 1)
        val endDateExpected = LocalDate.of(2025, 4, 3)

        assertAll(
            { assertThat(screeningPeriod.startDate).isEqualTo(startDateExpected) },
            { assertThat(screeningPeriod.endDate).isEqualTo(endDateExpected) },
        )
    }

    @Test
    fun `상영 기간은 종료 날짜가 시작 날짜보다 이전이면 예외를 던진다`() {
        val invalidStart = LocalDate.of(2025, 5, 1)
        val invalidEnd = LocalDate.of(2025, 4, 1)

        assertThrows<IllegalArgumentException> {
            ScreeningPeriod(invalidStart, invalidEnd)
        }
    }

    @Test
    fun `상영 기간내의 날짜 목록을 반환한다`() {
        val now = LocalDate.of(2025, 4, 2)
        val availableDates = screeningPeriod.getAvailableDates(now)

        assertThat(availableDates).containsExactly(
            LocalDate.of(2025, 4, 2),
            LocalDate.of(2025, 4, 3),
        )
    }
}
