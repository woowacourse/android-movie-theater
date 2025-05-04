package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.booking.ScreeningDates
import java.time.LocalDate

class ScreeningDatesTest {
    @Test
    fun `오늘보다 상영 시작 일자가 늦으면 상영 시작 일자부터 상영 종료일까지 반환한다`() {
        val screeningDates =
            ScreeningDates(
                listOf(
                    LocalDate.of(2025, 4, 18),
                    LocalDate.of(2025, 4, 19),
                    LocalDate.of(2025, 4, 20),
                ),
            ).bookableDates(
                LocalDate.of(2025, 4, 16),
            )

        assertEquals(
            screeningDates,
            listOf(
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 19),
                LocalDate.of(2025, 4, 20),
            ),
        )
    }

    @Test
    fun `이미 상영 중인 기간이면 오늘부터 상영 종료일까지 반환한다`() {
        val screeningDates =
            ScreeningDates(
                listOf(
                    LocalDate.of(2025, 4, 17),
                    LocalDate.of(2025, 4, 18),
                    LocalDate.of(2025, 4, 19),
                ),
            ).bookableDates(
                LocalDate.of(2025, 4, 18),
            )

        assertEquals(
            screeningDates,
            listOf(
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 19),
            ),
        )
    }
}
