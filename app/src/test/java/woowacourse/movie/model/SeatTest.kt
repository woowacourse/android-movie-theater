package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.model.seat.Col
import woowacourse.movie.model.seat.Row
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.SeatGrade

class SeatTest {
    @Test
    fun `좌석의 이름에 맞는 등급을 확인할 수 있다`() {
        val seat = SEAT_A1

        assertThat(seat.grade).isEqualTo(SeatGrade.B)
    }

    @Test
    fun `좌석의 이름이 아닌 행을 넣을 수 없다`() {
        assertThrows<IllegalArgumentException> { Seat(Row(5), Col(1)).grade }
        assertThrows<IllegalArgumentException> { Seat(Row(0), Col(6)).grade }
    }
}
