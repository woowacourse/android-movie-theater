package woowacourse.movie.model.ticket

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TicketCountTest {
    @Test
    fun `티켓 개수가 1이고 1을 더하면 값이 2인 티켓 개수를 반환한다`() {
        // given
        val ticketCount = TicketCount(1)

        // when
        val expected = TicketCount(2)

        // then
        Assertions.assertThat(ticketCount + 1).isEqualTo(expected)
    }

    @Test
    fun `티켓 개수가 3이고 1을 빼면 값이 2인 티켓 개수를 반환한다`() {
        // given
        val ticketCount = TicketCount(3)

        // when
        val expected = TicketCount(2)

        // then
        Assertions.assertThat(ticketCount - 1).isEqualTo(expected)
    }
}
