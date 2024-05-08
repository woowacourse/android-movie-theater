package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ScreeningScheduleTest {
    @Test
    fun `이틀동안 4번 상영을 하면 총 상영시간의 수는 8이다`() {
        val actual = ScreeningSchedule.STUB_A.totalScreeningTimesNum()
        val expected = 8
        assertThat(actual).isEqualTo(expected)
    }
}
