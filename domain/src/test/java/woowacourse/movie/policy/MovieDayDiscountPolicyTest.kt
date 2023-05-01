package woowacourse.movie.policy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.movie.util.Ticket
import java.time.LocalDateTime

class MovieDayDiscountPolicyTest {

    @ValueSource(ints = [10, 20, 30])
    @ParameterizedTest
    fun `무비데이인 경우 10퍼센트 할인된다`(day: Int) {
        // given : 10, 20, 30일은 무비데이다.
        val dateTime = LocalDateTime.of(2024, 3, day, 17, 0)
        val ticket = Ticket(dateTime)
        val policy: TicketDiscountPolicy = MovieDayDiscountPolicy()
        val expected = (ticket.price * 0.9).toInt()

        // when
        val actual = policy.discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ValueSource(ints = [1, 9, 11, 19, 21, 29, 31])
    @ParameterizedTest
    fun `무비데이가 아닌 경우 티켓은 정가이다`(day: Int) {
        // given
        val dateTime = LocalDateTime.of(2024, 3, day, 17, 0)
        val ticket = Ticket(dateTime)
        val policy: TicketDiscountPolicy = MovieDayDiscountPolicy()
        val expected = ticket.price

        // when
        val actual = policy.discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
