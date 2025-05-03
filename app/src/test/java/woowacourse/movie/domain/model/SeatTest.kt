package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import woowacourse.movie.domain.model.movie.TicketType
import woowacourse.movie.domain.model.theater.Seat

class SeatTest {
    @ParameterizedTest
    @EnumSource(value = TicketType::class)
    fun `좌석 등급별 가격을 알 수 있다`(ticketType: TicketType) {
        // given
        val seat = Seat(1, 1, ticketType)
        val actual = seat.price()

        // when
        val expected = ticketType.price

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
