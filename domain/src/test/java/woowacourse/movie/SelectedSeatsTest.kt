package woowacourse.movie

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat
import woowacourse.movie.ticket.SeatRank

class SelectedSeatsTest {
    private lateinit var selectedSeats: SelectedSeats

    @BeforeEach
    fun initSelectedSeat() {
        selectedSeats = SelectedSeats(3)
    }

    @Test
    fun `빈 좌석을 클릭하면 좌석이 추가된다`() {
        // given
        val seat = Seat(SeatRank.B, Position(0, 0))
        val expected: Int = selectedSeats.seats.size + 1

        // when
        selectedSeats.clickSeat(seat)
        val actual = selectedSeats.seats.size

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 클릭한 좌석을 클릭하면 좌석 선택이 해제된다`() {
        // given
        val seat = Seat(SeatRank.B, Position(0, 0))
        selectedSeats.clickSeat(seat)
        val expected: Int = selectedSeats.seats.size - 1

        // when
        selectedSeats.clickSeat(seat)
        val actual = selectedSeats.seats.size

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
