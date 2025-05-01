package woowacourse.movie.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.SEAT_A1
import woowacourse.movie.SEAT_A1_NOT_SELECTED
import woowacourse.movie.SEAT_A2
import woowacourse.movie.SEAT_C1
import woowacourse.movie.SEAT_E1

class SeatsTest {
    @Test
    fun `선택되지 않은 좌석이 포함될 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Seats(
                listOf(SEAT_A1_NOT_SELECTED),
            )
        }
    }

    @Test
    fun `선택된 좌석별 등급에 따라 예매가격을 확인할 수 있다`() {
        val seats =
            Seats(
                listOf(
                    SEAT_E1,
                    SEAT_A1,
                    SEAT_C1,
                ),
            )

        val expected = 37000
        assertEquals(expected, seats.amount)
    }

    @Test
    fun `좌석을 선택하면, 좌석의 선택 여부에 따라 리스트에 업데이트 된다`() {
        val seats =
            Seats(
                emptyList(),
            )
        val seat = SEAT_A1_NOT_SELECTED
        val newSeats = seats.toggle(seat, 3)

        val expectedSeat = SEAT_A1
        val expected = Seats(listOf(expectedSeat))
        assertEquals(expected, newSeats)
    }

    @Test
    fun `현재 선택한 좌석의 수가 예매 인원수보다 많을 경우 추가되지 않는다`() {
        val headCount = 2
        val seats =
            Seats(
                listOf(
                    SEAT_C1,
                    SEAT_A2,
                ),
            )
        val seat = SEAT_A1_NOT_SELECTED
        val newSeats = seats.toggle(seat, headCount)

        val expected = seats
        assertEquals(expected, newSeats)
    }

    @Test
    fun `현재 선택한 좌석의 수가 예매 인원수보다 적을 경우 추가된다`() {
        val headCount = 10
        val seats =
            Seats(
                listOf(
                    SEAT_A2,
                    SEAT_C1,
                ),
            )
        val seat = SEAT_A1_NOT_SELECTED
        val newSeats = seats.toggle(seat, headCount)

        val expected =
            Seats(
                listOf(
                    SEAT_A2,
                    SEAT_C1,
                    SEAT_A1,
                ),
            )
        assertEquals(expected, newSeats)
    }
}
