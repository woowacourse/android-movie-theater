package woowacourse.movie.policy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat
import woowacourse.movie.ticket.SeatRank
import woowacourse.movie.util.Ticket

class SeatPolicyTest {
    @Test
    fun `B등급 좌석의 가격은 10_000원이다`() {
        // given
        val ticket = Ticket(
            Seat(
                rank = SeatRank.B,
                position = Position(
                    row = 1,
                    column = 1
                )
            )
        )
        val expected = 10_000

        // when
        val actual: Int = RunningDiscountPolicy().discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A등급 좌석의 가격은 12_000원이다`() {
        // given
        val ticket = Ticket(
            Seat(
                rank = SeatRank.A,
                position = Position(
                    row = 1,
                    column = 1
                )
            )
        )
        val expected = 12_000

        // when
        val actual: Int = RunningDiscountPolicy().discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `S등급 좌석의 가격은 15_000원이다`() {
        // given
        val ticket = Ticket(
            Seat(
                rank = SeatRank.S,
                position = Position(
                    row = 1,
                    column = 1
                )
            )
        )
        val expected = 15_000

        // when
        val actual: Int = RunningDiscountPolicy().discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
