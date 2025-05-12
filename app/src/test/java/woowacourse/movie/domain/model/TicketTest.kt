package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.fixture.A1
import woowacourse.movie.fixture.B3
import woowacourse.movie.fixture.D2
import woowacourse.movie.fixture.E4
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG

class TicketTest {
    @Test
    fun `선택된 좌석이 없으면 총 금액은 0원이다`() {
        // given
        val seats = Seats(setOf())
        val ticket =
            Ticket(
                movie = HARRY_POTTER,
                theater = SEOLLEUNG,
                seats = seats,
            )

        // when
        val expected = 0

        // then
        assertThat(ticket.totalPrice()).isEqualTo(expected)
    }

    @Test
    fun `B3,D2 좌석이 있으면 총 금액은 25,000원이다`() {
        // given
        val seats = Seats(setOf(B3, D2))
        val ticket =
            Ticket(
                movie = HARRY_POTTER,
                theater = SEOLLEUNG,
                seats = seats,
            )

        // when
        val expected = 25_000

        // then
        assertThat(ticket.totalPrice()).isEqualTo(expected)
    }

    @Test
    fun `A1,B3,D2,E4 좌석이 있으면 총 금액은 47,000원이다`() {
        // given
        val seats = Seats(setOf(A1, B3, D2, E4))
        val ticket =
            Ticket(
                movie = HARRY_POTTER,
                theater = SEOLLEUNG,
                seats = seats,
            )

        // when
        val expected = 47_000

        // then
        assertThat(ticket.totalPrice()).isEqualTo(expected)
    }
}
