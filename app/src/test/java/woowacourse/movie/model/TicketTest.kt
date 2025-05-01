package woowacourse.movie.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A1_NOT_SELECTED
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
                Seats(emptyList()),
            )
        }

        assertDoesNotThrow {
            Ticket(
                theater = "선릉",
                "해리포터",
                HeadCount(0),
                LocalDate.of(2025, 4, 17),
                LocalTime.of(11, 0),
                Seats(emptyList()),
            )
        }
    }

    @Test
    fun `예매 인원에 맞는 금액을 계산한다`() {
        val ticket = createTicket(SEOLLEUNG, listOf())
        val expected = 0

        val actual = ticket.amount

        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원과  좌석별 등급에 맞는 금액을 계산한다`() {
        val seats = listOf(SEAT_A1, SEAT_A2)
        val ticket = createTicket(SEOLLEUNG, seats)

        val expected = 20000

        val actual = ticket.amount

        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원과 좌석별 등급에 맞는 금액을 계산한다`() {
        val seats = listOf(SEAT_A1, SEAT_A2)
        val ticket = createTicket(SEOLLEUNG, seats)

        val expected = 20000

        val actual = ticket.amount

        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원과 좌석별 등급에 맞는 금액을 계산한다2`() {
        val seats = listOf(SEAT_C1, SEAT_A2)
        val ticket = createTicket(SEOLLEUNG, seats)

        val expected = 25000

        val actual = ticket.amount

        assertEquals(expected, actual)
    }

    @Test
    fun `예매 인원이 0보다 큰 지 비교한다`() {
        val ticket = createTicket(SEOLLEUNG, listOf(SEAT_A1))

        val actual = ticket.isHeadCountValid()
        assertTrue(actual)

        val ticket2 = createTicket(SEOLLEUNG, listOf())
        val actual2 = ticket2.isHeadCountValid()
        assertFalse(actual2)
    }

    @Test
    fun `좌석이 선택되면, 해당하는 좌석이 이전에 선택되어있지 않은 경우 티켓의 좌석에 추가된다`() {
        val seat = SEAT_A1_NOT_SELECTED
        val ticket = createTicket(SEOLLEUNG, listOf(), 2)

        val newTicket = ticket.toggleSeat(seat)

        val expectedSeat = SEAT_A1
        val expectedTicket = ticket.copy(seats = Seats(listOf(expectedSeat)))

        assertEquals(expectedTicket, newTicket)
    }

    @Test
    fun `선택되어있던 좌석을 다시 선택하는 경우 티켓의 좌석에서 제거된다`() {
        val seat = SEAT_A1
        val ticket = createTicket(SEOLLEUNG, listOf(seat), 2)
        val newTicket = ticket.toggleSeat(seat)

        val expectedTicket = ticket.copy(seats = Seats(emptyList()))

        assertEquals(expectedTicket, newTicket)
    }
}
