package woowacourse.movie.domain.model.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDateTime

class TheaterTest {
    private lateinit var now: LocalDateTime
    private lateinit var theater: Theater

    @BeforeEach
    fun setUp() {
        now = LocalDateTime.now()
        val movieSchedule =
            MovieSchedule(
                movieId = 1,
                times =
                    listOf(
                        now.minusHours(2),
                        now.plusHours(1),
                        now.plusHours(3),
                    ),
            )

        theater =
            Theater(
                name = "선릉 극장",
                schedules = listOf(movieSchedule),
            )
    }

    @Test
    fun `특정 영화 ID의 현재 이후 시간만 정렬하여 반환한다`() {
        val result = theater.getAvailableShowTimesFor(1, now)
        val expected =
            listOf(
                now.plusHours(1),
                now.plusHours(3),
            )

        assertAll(
            { assertThat(result.size).isEqualTo(2) },
            { assertThat(result).isEqualTo(expected) },
        )
    }

    @Test
    fun `일치하는 영화가 없으면 빈 리스트를 반환한다`() {
        val result = theater.getAvailableShowTimesFor(99, now)

        assertThat(result).isEmpty()
    }
}
