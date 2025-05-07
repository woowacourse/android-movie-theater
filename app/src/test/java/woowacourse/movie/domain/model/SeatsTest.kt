package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SeatsTest {
    @Test
    fun `좌석을_추가할_수_있다`() {
        // given
        val seats = Seats()
        seats.reserve(Seat(1, 1))
        val actual = seats.reservingSeats.size

        // when
        val expected = 1

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미_점유된_좌석인지_알_수_있다`() {
        // given
        val targetSeat = Seat(1, 1)
        val seats = Seats().apply { reserve(targetSeat) }
        val actual = seats.isReservedSeat(targetSeat)

        // when
        val expected = true

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `좌석을_선택_취소할_수_있다`() {
        // given
        val targetSeat = Seat(1, 1)
        val seats =
            Seats().apply {
                reserve(targetSeat)
                cancelReserve(targetSeat)
            }
        val actual = seats.reservingSeats.contains(targetSeat)

        // when
        val expected = false

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `좌석_가격의_총합을_알_수_있다`() {
        // given
        val seats =
            Seats().apply {
                reserve(Seat(1, 2, TicketType.B_GRADE))
                reserve(Seat(3, 3, TicketType.S_GRADE))
                reserve(Seat(5, 5, TicketType.A_GRADE))
            }
        val actual = seats.totalPrice()

        // when
        val expected =
            TicketType.B_GRADE.price + TicketType.S_GRADE.price + TicketType.A_GRADE.price

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
