package woowacourse.movie.domain.screening

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class ScreeningRangeTest {

    @Test
    fun `특정 시각이 상영 기간 날짜 안에 포함되는지 알 수 있다`() {
        val startDate = LocalDate.of(2021, 3, 31)
        val endDate = LocalDate.of(2021, 4, 1)
        val screeningRange = ScreeningRange(startDate, endDate)
        val dateTime = LocalDateTime.of(2021, 3, 31, 0, 0)

        val actual = screeningRange.contains(dateTime)

        assertThat(actual).isTrue
    }

    @Test
    fun `상영 기간의 시작 날짜가 마지막 날짜 이후라면 에러가 발생한다`() {
        val startDate = LocalDate.of(2021, 3, 31)
        val endDate = LocalDate.of(2021, 3, 1)

        assertThatIllegalArgumentException()
            .isThrownBy { ScreeningRange(startDate, endDate) }
            .withMessage("시작 날짜는 마지막 날짜 이후일 수 없습니다.")
    }
}
