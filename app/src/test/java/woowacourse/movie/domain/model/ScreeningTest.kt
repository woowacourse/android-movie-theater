package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyScreening
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScreeningTest {
    private val screening = DummyScreening.dummyScreenings[0]

    @Test
    fun `현재 시간보다 이후의 시간을 반환할 수 있다`() {
        // given
        val now = LocalDateTime.of(2025, 5, 29, 10, 0, 0)

        // when
        val availableTimes = screening.availableTimes(now, LocalDate.of(2025, 5, 29))

        // then
        assertThat(availableTimes).isEqualTo(
            listOf(
                LocalTime.of(11, 0, 0),
            ),
        )
    }

    @Test
    fun `현재 날짜가 아닌 날을 선택하면 모든 가능한 시간을 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 5, 29, 10, 0, 0)

        // when
        val availableTimes = screening.availableTimes(now, LocalDate.of(2025, 5, 30))

        // then
        assertThat(availableTimes).isEqualTo(
            listOf(
                LocalTime.of(9, 0, 0),
                LocalTime.of(11, 0, 0),
            ),
        )
    }

    @Test
    fun `모든 가능한 상영시간의 개수를 반환할 수 있다`() {
        // given - when
        val size = screening.availableTimesSize
        // then
        assertThat(size).isEqualTo(2)
    }
}
