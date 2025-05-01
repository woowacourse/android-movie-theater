package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.fixture.oneByOneSeat
import woowacourse.movie.domain.fixture.oneByTowSeat
import woowacourse.movie.domain.model.seat.Seats

class SeatsTest {
    @Test
    fun `새로운 좌석이 추가된다`() {
        // given
        val seats = Seats()

        // when
        seats.addSeat(oneByOneSeat)

        // then
        assertTrue(seats.item.contains(oneByOneSeat))
    }

    @Test
    fun `선택한 좌석이 제거된다`() {
        // given
        val seats = Seats()

        // when
        seats.addSeat(oneByOneSeat)
        seats.removeSeat(oneByOneSeat)

        // then
        assertTrue(seats.item.isEmpty())
    }

    @Test
    fun `B등급 좌석 두 개를 예매하면 총 가격 2만원을 반환한다`() {
        // given
        val seats = Seats()

        seats.addSeat(oneByOneSeat)
        seats.addSeat(oneByTowSeat)

        // when
        val totalPrice = seats.bookingPrice()

        // then
        assertEquals(20000, totalPrice)
    }

    @Test
    fun `선택한 예매 인원수만큼 예매하지 않았으면 참을 반환한다`() {
        // given
        val seats = Seats()
        seats.addSeat(oneByOneSeat)

        // when
        val result = seats.isNotSelectDone(2)

        // then
        assertTrue(result)
    }

    @Test
    fun `선택한 예매 인원수 만큼 예매 했으면 거짓을 반환한다`() {
        // given
        val seats = Seats()
        seats.addSeat(oneByOneSeat)
        seats.addSeat(oneByTowSeat)

        // when
        val result = seats.isNotSelectDone(2)

        // then
        assertFalse(result)
    }

    @Test
    fun `예매된 좌석이 없으면 총 가격은 0원이다`() {
        // given
        val seats = Seats()

        // when
        val totalPrice = seats.bookingPrice()

        // then
        assertEquals(0, totalPrice)
    }

    @Test
    fun `하나의 좌석만 예매하면 가격이 해당 좌석 가격으로 계산된다`() {
        // given
        val seats = Seats()

        // when
        seats.addSeat(oneByOneSeat)
        val totalPrice = seats.bookingPrice()

        // then
        assertEquals(10000, totalPrice)
    }

    @Test
    fun `좌석을 추가할 수 있다면 좌석이 추가된다`() {
        // given
        val seats = Seats()

        // when
        seats.toggleSeat(oneByOneSeat)

        // then
        assertTrue(seats.item.contains(oneByOneSeat))
    }
}
