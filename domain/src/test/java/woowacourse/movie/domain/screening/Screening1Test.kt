package woowacourse.movie.domain.screening

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.theater.Point
import woowacourse.movie.domain.theater.Theater
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class Screening1Test {

    @Test
    fun `상영의 아이디가 설정된 상태에서 아이디를 다시 변경하면 변경되지 않는다`() {
        val theater = Theater("잠실", 5, 4).apply { id = 1L }
        val time = LocalTime.of(14, 0)
        val timeTable = TimeTable(mapOf(theater to listOf(time)))
        val screening = Screening1(
            ScreeningRange(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 31)),
            timeTable,
            Movie("제목", Minute(152), "요약")
        )
        screening.id = 1L

        screening.id = 2L

        assertThat(screening.id).isEqualTo(1L)
    }

    @Test
    fun `상영하지 않는 날짜를 예매하면 에러가 발생한다`() {
        val theater = Theater("잠실", 5, 4).apply { id = 1L }
        val time = LocalTime.of(14, 0)
        val timeTable = TimeTable(mapOf(theater to listOf(time)))
        val screening = Screening1(
            ScreeningRange(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 31)),
            timeTable,
            Movie("제목", Minute(152), "요약")
        )
        val notScreeningDateTime = LocalDateTime.of(2021, 4, 1, 0, 0)

        assertThatIllegalArgumentException()
            .isThrownBy { screening.reserve(notScreeningDateTime, theater, listOf(Point(1, 1))) }
            .withMessage("${theater.name} 극장에서 ${notScreeningDateTime}에 상영하지 않습니다.")
    }
}