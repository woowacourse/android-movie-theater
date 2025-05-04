package woowacourse.movie.domain.model.cinema

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SeatsTest {
    @Nested
    inner class `Screen 생성 시` {
        @Test
        fun `row와 col 크기에 맞게 좌석이 생성된다`() {
            val seatingSize = SeatingSize(5, 4)
            val seats = Seats(seatingSize)

            assertThat(seats.seats.size).isEqualTo(20)
        }

        @Test
        fun `row에 따라 좌석 타입이 올바르게 설정된다`() {
            val seatingSize = SeatingSize(5, 4)
            val seats = Seats(seatingSize)

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
    }
}
