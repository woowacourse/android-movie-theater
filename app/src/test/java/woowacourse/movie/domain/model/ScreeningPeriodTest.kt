package woowacourse.movie.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import java.time.LocalDate

class ScreeningPeriodTest {
    @Test
    fun `오늘보다 상영 시작 일자가 늦으면 상영 시작 일자부터 상영 종료일까지 반환한다`() {
        val screeningPeriod =
            ScreeningPeriod(
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 20),
            ).bookingDates(LocalDate.of(2025, 4, 16))

        assertEquals(
            screeningPeriod,
            listOf(
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 19),
                LocalDate.of(2025, 4, 20),
            ),
        )
    }

    @Test
    fun `이미 상영 중인 기간이면 오늘부터 상영 종료일까지 반환한다`() {
        val screeningPeriod =
            ScreeningPeriod(
                LocalDate.of(2025, 4, 15),
                LocalDate.of(2025, 4, 17),
            ).bookingDates(LocalDate.of(2025, 4, 16))

        assertEquals(
            screeningPeriod,
            listOf(
                LocalDate.of(2025, 4, 16),
                LocalDate.of(2025, 4, 17),
            ),
        )
    }
}
