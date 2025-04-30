package woowacourse.movie.domain.model

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class ScreeningDateTimeTest {
    @Test
    fun `특정 상영일자가 입력받은 상영일자 이후 인지 알 수 있다`() {
        // given
        val screeningDateTime = ScreeningDateTime(LocalDate.of(2025, 4, 30), LocalTime.of(10, 0))
        val pastDate = LocalDate.of(2025, 4, 1)
        val pastTime = LocalTime.of(10, 0)

        assertTrue(screeningDateTime.isScreening(pastDate, pastTime))
    }
}