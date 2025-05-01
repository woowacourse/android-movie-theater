package woowacourse.movie.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.createMovie
import java.time.LocalDate
import java.time.LocalTime

class SchedulerTest {
    private lateinit var movie: Movie

    @BeforeEach
    fun setUp() {
        movie =
            createMovie(HARRY_POTTER)
    }

    @Test
    fun `상영일자에 맞는 상영일들을 가져온다`() {
        val expected =
            localDates(
                "2025-05-10",
                "2025-05-11",
                "2025-05-12",
                "2025-05-13",
                "2025-05-14",
                "2025-05-15",
            )

        val actual = Scheduler.screeningPeriods(movie)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현재 시간을 기준으로 예매 가능한 시간들을 가져온다`() {
        val expected =
            localTimes("11:00", "12:00")

        val actual = Scheduler.screeningTimes(LocalDate.of(2025, 4, 10), expected)

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private fun localDates(vararg dates: String): List<LocalDate> {
        return dates.map { date ->
            val (year, month, day) = date.split("-").map { it.toInt() }
            LocalDate.of(year, month, day)
        }
    }

    private fun localTimes(vararg times: String): List<LocalTime> {
        return times.map { time ->
            val (hour, min) = time.split(":").map { it.toInt() }

            if (hour == 24 && min == 0) {
                LocalTime.MIDNIGHT
            } else {
                LocalTime.of(hour, min)
            }
        }
    }
}
