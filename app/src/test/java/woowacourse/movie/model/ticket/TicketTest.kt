package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats

class TicketTest {
    @Test
    fun `선택한 좌석의 등급에 따른 가격을 합해서 총 결제 금액을 반환한다`() {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 1, Grade.B))
        seats.manageSelected(true, Seat('C', 1, Grade.S))

        val ticket = Ticket()
        val actual = ticket.calculateAmount(seats)

        assertThat(actual).isEqualTo(25_000)
    }
}
