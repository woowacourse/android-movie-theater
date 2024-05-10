package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.SeatSelection
import woowacourse.movie.model.seats.TheaterSeat

class SeatSelectionTest {
    @Test
    fun `선택한 좌석의 등급에 따른 가격을 합해서 총 결제 금액을 반환한다`() {
        val seatSelection = SeatSelection()
        seatSelection.manageSelected(true, TheaterSeat('A', 1, Grade.B))
        seatSelection.manageSelected(true, TheaterSeat('C', 1, Grade.S))

        val actual = seatSelection.calculateAmount()

        assertThat(actual).isEqualTo(25_000)
    }
}
