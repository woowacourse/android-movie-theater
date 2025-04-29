package woowacourse.movie.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ScheduleTimeTest {
    @Test
    fun `영화 시간표는 현재 시간 이후의 영화 시간만 반환한다`() {
        // given
        val scheduleTime =
            ScheduleTime(
                time = listOf(LocalDateTime.of(2025, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 5, 0)),
                currentTime = LocalDateTime.of(2025, 1, 1, 3, 1),
            )
        // when
        val actual = scheduleTime.afterCurrentTimeSchedule()
        val expected = listOf(LocalDateTime.of(2025, 1, 1, 5, 1))
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
