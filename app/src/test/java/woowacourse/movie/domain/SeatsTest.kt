package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_C1

class SeatsTest {
    @Test
    fun `새로운 좌석이 추가된다`() {
        // when
        val seats = Seats()

        // given
        seats.addSeat(SEAT_A1)

        // then
        assertTrue(seats.item.contains(SEAT_A1))
    }

    @Test
    fun `선택한 좌석이 제거된다`() {
        // when
        val seats = Seats()

        // given
        seats.addSeat(SEAT_A1)
        seats.removeSeat(SEAT_A1)

        // then
        assertTrue(seats.item.isEmpty())
    }

    @Test
    fun `B등급 좌석과 S등급 좌석을 하나씩 예매하면 총 가격 35000원을 반환한다`() {
        // when
        val seats = Seats()

        seats.addSeat(SEAT_A1)
        seats.addSeat(SEAT_C1)

        // given
        val totalPrice = seats.totalPrice()

        // then
        assertEquals(25000, totalPrice)
    }

    @Test
    fun `선택한 예매 인원수만큼 예매하지 않았으면 참을 반환한다`() {
        // when
        val seats = Seats()
        seats.addSeat(SEAT_A1)

        // given
        val result = seats.isNotSelectDone(2)

        // then
        assertTrue(result)
    }

    @Test
    fun `선택한 예매 인원수 만큼 예매 했으면 거짓을 반환한다`() {
        // when
        val seats = Seats()
        seats.addSeat(SEAT_A1)
        seats.addSeat(SEAT_C1)

        // given
        val result = seats.isNotSelectDone(2)

        // then
        assertFalse(result)
    }

    @Test
    fun `예매된 좌석이 없으면 총 가격은 0원이다`() {
        // when
        val seats = Seats()

        // given
        val totalPrice = seats.totalPrice()

        // then
        assertEquals(0, totalPrice)
    }

    @Test
    fun `하나의 좌석만 예매하면 가격이 해당 좌석 가격으로 계산된다`() {
        // when
        val seats = Seats()
        seats.addSeat(SEAT_A1)

        // given
        val totalPrice = seats.totalPrice()

        // then
        assertEquals(10000, totalPrice)
    }

    @Test
    fun `좌석을 추가할 수 있을 때 좌석이 추가된다`() {
        // when
        val seats = Seats()

        // given
        seats.toggleSeat(SEAT_A1)

        // then
        assertTrue(seats.item.contains(SEAT_A1))
    }
}
