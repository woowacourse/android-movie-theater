package woowacourse.movie.domain.model.theater

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE
import woowacourse.movie.domain.model.MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN
import woowacourse.movie.domain.model.SCHEDULE_2025_04_10_TIME_1300
import woowacourse.movie.domain.model.SCHEDULE_2025_04_15_TIME_1630
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterTest {
    @Test
    fun `극장의 모든 영화 스케줄 중에서 특정 영화의 스케줄만 확인할 수 있다`() {
        val theater =
            Theater(
                name = "선릉 극장",
                allSchedules =
                    mapOf(
                        MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE to
                            listOf(
                                SCHEDULE_2025_04_15_TIME_1630,
                            ),
                        MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN to
                            listOf(
                                SCHEDULE_2025_04_10_TIME_1300,
                            ),
                    ),
            )
        val todayDateTime = LocalDateTime.of(2025, 4, 10, 12, 0)
        val theaterOfAZKABAN: Theater =
            theater.bookableTheater(MOVIE_HARRY_POTTER_AND_THE_PRISONER_OF_AZKABAN, todayDateTime)

        val actualSchedulesSize = theaterOfAZKABAN.allSchedules.size
        val test =
            theaterOfAZKABAN.allSchedules
        val actualAvailableTimes: List<LocalTime> =
            theaterOfAZKABAN.allSchedules.values
                .flatten()
                .map { it.screeningTimeSchedule.time }
                .flatten()

        val expectedScheduleSize = 1
        val expectedSchedules = listOf(LocalTime.of(13, 0))

        assertThat(actualSchedulesSize).isEqualTo(expectedScheduleSize)
        assertThat(actualAvailableTimes).isEqualTo(expectedSchedules)
    }
}
