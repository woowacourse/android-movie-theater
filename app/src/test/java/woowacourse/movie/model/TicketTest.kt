package woowacourse.movie.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import java.time.LocalDate
import java.time.LocalTime

class TicketTest {
    @Test
    fun `예매한 영화 제목은 비어있을 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Ticket(
                theater = "선릉",
                "",
                HeadCount(0),
                LocalDate.of(2025, 4, 17),
                LocalTime.of(11, 0),
                Seats(mutableSetOf()),
            )
        }

        assertDoesNotThrow {
            Ticket(
                theater = "선릉",
                "해리포터",
                HeadCount(0),
                LocalDate.of(2025, 4, 17),
                LocalTime.of(11, 0),
                Seats(mutableSetOf()),
            )
        }
    }

    @Test
    fun `예매 인원에 맞는 금액을 계산한다`() {
        // given
        val ticket = createTicket(SEOLLEUNG, listOf())

        // when
        val actual = ticket.amount
        val expected = 0

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원과 좌석별 등급에 맞는 금액을 계산한다`() {
        // given
        val seats = listOf(SEAT_A1, SEAT_A2)
        val ticket = createTicket(SEOLLEUNG, seats)

        // when
        val actual = ticket.amount
        val expected = 20000

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원과 좌석별 등급에 맞는 금액을 계산한다2`() {
        // given
        val seats = listOf(SEAT_C1, SEAT_A2)
        val ticket = createTicket(SEOLLEUNG, seats)

        // when
        val actual = ticket.amount
        val expected = 25000

        // then
        assertEquals(expected, actual)
    }
}
