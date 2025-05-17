package woowacourse.movie.domain.model.theater

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.dummy.DUMMY_MOVIES
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScreeningTimeScheduleTest {
    @Test
    fun `오늘이 상영날인 경우 이미 지난 시간을 제외하고 상영 시간대를 구할 수 있다`() {
        val screeningTimeSchedule =
            ScreeningTimeSchedule(
                date = LocalDate.of(2025, 5, 8),
                time =
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(20, 0),
                    ),
            )
        val todayDateTime = LocalDateTime.of(2025, 5, 8, 15, 0)

        val actual = screeningTimeSchedule.bookableSchedules(DUMMY_MOVIES.first(), todayDateTime)

        val expectedSize = 1
        val expectedTime = LocalTime.of(20, 0)

        assertThat(actual?.time?.size).isEqualTo(expectedSize)
        assertThat(actual?.time?.first()).isEqualTo(expectedTime)
    }
}
