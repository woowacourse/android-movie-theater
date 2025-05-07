package woowacourse.movie.domain.model.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.movie.domain.model.cinema.MovieSchedule
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.Poster
import woowacourse.movie.domain.model.movie.RunningTime
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieScheduleTest {
    private val dummyMovie =
        Movie(
            id = 1,
            title = "Dummy Movie",
            screeningPeriod = ScreeningPeriod(LocalDate.now(), LocalDate.now().plusDays(2)),
            poster = Poster.Url(""),
            runningTime = RunningTime(152),
        )

    @Test
    fun `주어진 시간 이후의 상영 시간만 반환한다`() {
        val baseTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0))
        val times =
            listOf(
                baseTime.minusHours(2),
                baseTime,
                baseTime.plusHours(1),
                baseTime.plusHours(2),
            )
        val schedule = MovieSchedule(movieId = 1, times = times)

        val result = schedule.getTimesAfter(baseTime)

        assertAll(
            { assertThat(2).isEqualTo(result.size) },
            { assertThat(result.all { it.isAfter(baseTime) }).isTrue() },
        )
    }
}
