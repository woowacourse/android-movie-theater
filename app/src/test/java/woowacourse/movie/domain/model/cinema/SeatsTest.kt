package woowacourse.movie.domain.model.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SeatsTest {
    private val seats = Seats(SeatingSize(5, 4))

    @Test
    fun `row와 col 크기에 맞게 좌석이 생성된다`() {
        assertThat(seats.seats.size).isEqualTo(20)
    }

    @Test
    fun `row에 따라 좌석 타입이 올바르게 설정된다`() {
        seats.seats.forEach { seat ->
            val expectedSeatType =
                when (seat.row) {
                    0, 1 -> SeatType.B_CLASS
                    2, 3 -> SeatType.S_CLASS
                    else -> SeatType.A_CLASS
                }
            assertThat(expectedSeatType).isEqualTo(seat.type)
        }
    }

    @Test
    fun `좌석 수를 알 수 있다`() {
        val result = seats.size
        val expected = 20

        assertThat(result).isEqualTo(expected)
    }
}
