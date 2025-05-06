package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterSchedulesTest {
    private lateinit var theaterSchedules: TheaterSchedules

    @BeforeEach
    fun setUp() {
        theaterSchedules =
            TheaterSchedules(
                _schedules =
                    mutableMapOf(
                        1L to
                            setOf(
                                MovieSchedule(LocalDateTime.of(2025, 4, 30, 10, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 6, 10, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 6, 11, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 6, 12, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 7, 20, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 8, 15, 0)),
                            ),
                        2L to
                            setOf(
                                MovieSchedule(LocalDateTime.of(2025, 5, 6, 10, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 8, 12, 0)),
                            ),
                        3L to
                            setOf(
                                MovieSchedule(LocalDateTime.of(2025, 5, 7, 10, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0)),
                                MovieSchedule(LocalDateTime.of(2025, 5, 8, 12, 0)),
                            ),
                    ),
            )
    }

    @ParameterizedTest
    @CsvSource(value = ["1,3", "2,3", "3,2", "4,0"])
    fun `상영가능한 중복되지 않은 날짜들을 확인한다`(
        movieId: String,
        _expected: String,
    ) {
        val dateTime = LocalDateTime.of(2025, 5, 6, 11, 0)
        val expected = _expected.toInt()

        val dates: List<LocalDate> = theaterSchedules.screeningDates(movieId.toLong(), dateTime)

        assertThat(dates.size).isEqualTo(expected)
    }

    @Test
    fun `상영일자에 상영가능한 시간들을 확인한다`() {
        val dateTime = LocalDateTime.of(2025, 5, 6, 10, 0)
        val expected: List<LocalTime> =
            listOf(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
            )

        val actual = theaterSchedules.screeningTimes(1L, dateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `영화 id와 날짜, 시간으로 해당 영화 스케줄을 반환한다`() {
        val dateTime = LocalDateTime.of(2025, 5, 7, 11, 0)
        val movieId = 2L
        val expected = MovieSchedule(LocalDateTime.of(2025, 5, 7, 11, 0))

        val actual: MovieSchedule? =
            theaterSchedules.movieScheduleByMovieIdAndDateTime(movieId, dateTime)

        assertThat(actual).isEqualTo(expected)
    }
}
