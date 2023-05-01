package woowacourse.movie.policy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.movie.util.Ticket
import java.time.LocalDateTime

class RunningDiscountPolicyTest {
    @Test
    fun `무비데이이고 조조 혹은 야간이면 10퍼센트 할인과 2000원 할인이 적용된다`() {
        // given
        val dateTime = LocalDateTime.of(2024, 1, 10, 11, 0)
        val ticket = Ticket(dateTime)

        // when
        val actual = RunningDiscountPolicy.discount(ticket.price, ticket.bookedDateTime)

        // then
        val expected = (ticket.price * 0.9).toInt() - 2000
        assertThat(actual).isEqualTo(expected)
    }

    @ValueSource(ints = [10, 20, 30])
    @ParameterizedTest
    fun `무비데이고 조조 혹은 야간이 아니면 10퍼센트 할인된다`(day: Int) {
        // given
        val dateTime = LocalDateTime.of(2024, 3, day, 17, 0)
        val ticket = Ticket(dateTime)
        val expected = (ticket.price * 0.9).toInt()

        // when
        val actual = RunningDiscountPolicy.discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ValueSource(ints = [9, 10, 11, 20, 21, 22, 23])
    @ParameterizedTest
    fun `할인 시간이고 무비데이가 아니면 2000원 할인된다`(hour: Int) {
        // given
        val dateTime = LocalDateTime.of(2024, 3, 1, hour, 0)
        val ticket = Ticket(dateTime)
        val expected = ticket.price - 2000

        // when
        val actual = RunningDiscountPolicy.discount(ticket.price, ticket.bookedDateTime)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
