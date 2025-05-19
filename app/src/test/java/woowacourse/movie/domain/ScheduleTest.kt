package woowacourse.movie.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.booking.Schedule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScheduleTest {
    @Test
    fun `첫 상영 스케줄이 오늘보다 늦으면 상영 기간 전체를 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 17, 0, 0)
        val schedule =
            Schedule(
                listOf(
                    LocalDateTime.of(2025, 4, 18, 12, 0),
                    LocalDateTime.of(2025, 4, 19, 12, 0),
                    LocalDateTime.of(2025, 4, 20, 12, 0),
                ),
            )

        // when
        val actual = schedule.bookableDates(now)
        val expected =
            listOf(
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 19),
                LocalDate.of(2025, 4, 20),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 상영 중이면 오늘부터 상영 종료일까지 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 19, 0, 0)
        val schedule =
            Schedule(
                listOf(
                    LocalDateTime.of(2025, 4, 18, 12, 0),
                    LocalDateTime.of(2025, 4, 19, 12, 0),
                    LocalDateTime.of(2025, 4, 20, 12, 0),
                ),
            )

        // when
        val actual = schedule.bookableDates(now)
        val expected =
            listOf(
                LocalDate.of(2025, 4, 19),
                LocalDate.of(2025, 4, 20),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 상영 중이고 오늘 남은 상영 스케줄이 없으면 내일부터 상영 종료일까지 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 19, 23, 59)
        val schedule =
            Schedule(
                listOf(
                    LocalDateTime.of(2025, 4, 18, 12, 0),
                    LocalDateTime.of(2025, 4, 19, 12, 0),
                    LocalDateTime.of(2025, 4, 20, 12, 0),
                ),
            )

        // when
        val actual = schedule.bookableDates(now)
        val expected =
            listOf(
                LocalDate.of(2025, 4, 20),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택된 예매 날짜가 오늘이면 이미 지나간 상영 시간을 제외한 상영 시간을 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 18, 12, 0)
        val selectedDate = LocalDate.of(2025, 4, 18)
        val schedule =
            Schedule(
                listOf(
                    LocalDateTime.of(2025, 4, 18, 11, 0),
                    LocalDateTime.of(2025, 4, 18, 12, 0),
                    LocalDateTime.of(2025, 4, 18, 13, 0),
                ),
            )

        // when
        val actual = schedule.bookableTimes(selectedDate, now)
        val expected =
            listOf(
                LocalTime.of(13, 0),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선택된 예매 날짜가 오늘이 아니면 모든 예매 시간을 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 18, 12, 0)
        val selectedDate = LocalDate.of(2025, 4, 19)
        val schedule =
            Schedule(
                listOf(
                    LocalDateTime.of(2025, 4, 19, 11, 0),
                    LocalDateTime.of(2025, 4, 19, 12, 0),
                    LocalDateTime.of(2025, 4, 19, 13, 0),
                ),
            )

        // when
        val actual = schedule.bookableTimes(selectedDate, now)
        val expected =
            listOf(
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
            )

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
