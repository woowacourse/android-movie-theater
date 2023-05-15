package woowacourse.movie.domain.screening

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.theater.Theater
import java.time.LocalTime

internal class TimeTableTest {

    @Test
    fun `시간표에서 특정 극장과 특정 시각에 상영하는지 알 수 있다`() {
        val theater = Theater("잠실",5, 4).apply { id = 1L }
        val time = LocalTime.of(14, 0)
        val timeTable = TimeTable(mapOf(theater to listOf(time)))

        val actual = timeTable.screenOn(theater, time)

        assertThat(actual).isTrue
    }
}