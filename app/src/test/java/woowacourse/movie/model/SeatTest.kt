package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.fixture.SEAT_A1

class SeatTest {
    @Test
    fun `좌석의 이름에 맞는 등급을 확인할 수 있다`() {
        // given
        val seat = SEAT_A1

        // when
        val actual = seat.grade
        val expected = SeatGrade.B

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `좌석의 열과 행은 최댓값을 초과할 수 없다`() {
        assertThrows<IllegalArgumentException> { Seat(5, 5).grade }
        assertThrows<IllegalArgumentException> { Seat(6, 8).grade }
    }
}
