package woowacourse.movie.domain.model

import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MovieScheduleTest {
    private lateinit var movieSchedule: MovieSchedule

    @BeforeEach
    fun setUp()  {
        val screeningDateTime = LocalDateTime.of(2025, 5, 10, 10, 0)
        movieSchedule = MovieSchedule(screeningDateTime)
    }

    @ParameterizedTest(name = "예매 하고 싶은 날짜 : {0} 결과 : {1}")
    @CsvSource(value = ["2025.5.9.10.0,true", "2025.5.10.10.0,true", "2025.5.11.12.0,false"])
    fun `상영 가능한 날짜 인지 판단한다`(
        wantDateTime: String,
        _expected: String,
    ) {
        val (year, month, day, hour, minute) = wantDateTime.split(".").map { it.toInt() }
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val expected = _expected.toBoolean()

        val actual = movieSchedule.isScreeningDate(dateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "예매 하려는 날짜 : {0} 결과 : {1}")
    @CsvSource(value = ["2025.5.10.9.30,false", "2025.5.10.10.0,true", "2025.5.10.12.0,true", "2025.5.11.12.0,false"])
    fun `오늘 상영 가능한 시간인지 판단한다`(
        wantDateTime: String,
        _expected: String,
    ) {
        val (year, month, day, hour, minute) = wantDateTime.split(".").map { it.toInt() }
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val expected = _expected.toBoolean()

        val actual = movieSchedule.isTodayAvailableScreening(dateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "예매 하려는 날짜 : {0} 결과 : {1}")
    @CsvSource(value = ["2025.5.10.9.30,true", "2025.5.10.10.0,true", "2025.5.10.12.0,true", "2025.5.11.12.0,false"])
    fun `미래 상영일자를 선택하면 날짜 기준으로 동일한 날짜인지 판단한다`(
        wantDateTime: String,
        _expected: String,
    ) {
        val (year, month, day, hour, minute) = wantDateTime.split(".").map { it.toInt() }
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val expected = _expected.toBoolean()

        val actual = movieSchedule.isFutureAvailableScreeningByDate(dateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest(name = "비교 날짜 : {0} 결과 : {1}")
    @CsvSource(value = ["2025.5.10.10.0,true", "2025.5.10.10.30,false"])
    fun `날짜, 시간으로 영화 스케줄을 판단한다`(
        wantDateTime: String,
        _expected: String,
    ) {
        val (year, month, day, hour, minute) = wantDateTime.split(".").map { it.toInt() }
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val expected = _expected.toBoolean()

        val actual = movieSchedule.isEqual(dateTime)

        assertThat(actual).isEqualTo(expected)
    }
}
