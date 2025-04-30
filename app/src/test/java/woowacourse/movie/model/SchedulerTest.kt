package woowacourse.movie.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class SchedulerTest {
    private lateinit var movie: Movie
    private lateinit var scheduler: Scheduler

    @BeforeEach
    fun setUp() {
        movie =
            Movie(
                imageSource = "harry_potter.png",
                title = "해리 포터와 마법사의 돌",
                runningTime = 152,
                screeningStartDate = LocalDate.of(2025, 4, 1),
                screeningEndDate = LocalDate.of(2025, 4, 25),
            )

        scheduler =
            Scheduler(
                movie,
                listOf(
                    LocalTime.of(11, 0),
                    LocalTime.of(12, 0),
                ),
                LocalDate.of(2025, 4, 20), LocalTime.of(8, 0),
            )
    }

    @Test
    fun `상영일자에 맞는 상영일들을 가져온다`() {
        val expected =
            localDates(
                "2025-04-20",
                "2025-04-21",
                "2025-04-22",
                "2025-04-23",
                "2025-04-24",
                "2025-04-25",
            )

        val actual = scheduler.screeningPeriods()

        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현재 시간을 기준으로 예매 가능한 시간들을 가져온다`() {
        val expected =
            localTimes("11:00", "12:00")

        val actual = scheduler.screeningTimes(LocalDate.of(2025, 4, 10))

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
