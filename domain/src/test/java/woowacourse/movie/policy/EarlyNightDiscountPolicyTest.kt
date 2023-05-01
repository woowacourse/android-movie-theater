package woowacourse.movie.policy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.movie.util.Ticket
import java.time.LocalDateTime

class EarlyNightDiscountPolicyTest {

    @ValueSource(ints = [9, 10, 11])
    @ParameterizedTest
    fun `조조인 경우 티켓이 2000원 할인된다`(hour: Int) {
        val dateTime = LocalDateTime.of(2024, 3, 1, hour, 0)
        val ticket = Ticket(dateTime)
        val policy: TicketDiscountPolicy = EarlyNightDiscountPolicy()
        val expected = ticket.price - 2000

        val actual = policy.discount(ticket.price, ticket.bookedDateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @ValueSource(ints = [20, 21, 22, 23])
    @ParameterizedTest
    fun `심야인 경우 티켓이 2000원 할인된다`(hour: Int) {
        val dateTime = LocalDateTime.of(2024, 3, 1, hour, 0)
        val ticket = Ticket(dateTime)
        val policy: TicketDiscountPolicy = EarlyNightDiscountPolicy()
        val expected = ticket.price - 2000

        val actual = policy.discount(ticket.price, ticket.bookedDateTime)

        assertThat(actual).isEqualTo(expected)
    }

    @ValueSource(ints = [12, 13, 14, 15, 16, 17, 18, 19])
    @ParameterizedTest
    fun `조조와 야간이 아닌 경우 티켓은 정가이다`(hour: Int) {
        val dateTime = LocalDateTime.of(2024, 3, 1, hour, 0)
        val ticket = Ticket(dateTime)
        val policy: TicketDiscountPolicy = EarlyNightDiscountPolicy()
        val expected = ticket.price

        val actual = policy.discount(ticket.price, ticket.bookedDateTime)

        assertThat(actual).isEqualTo(expected)
    }
}
