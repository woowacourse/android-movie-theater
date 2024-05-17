package woowacourse.movie.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ReservationTest {
    @Test
    fun `좌석 갯수가 티켓 수와 다르면 예외을 던진다`() {
        assertThrows<IllegalArgumentException> {
            Reservation(
                1,
                Screen.NULL,
                Ticket(1),
                Seats(
                    Seat(Position(0, 0), Grade.S),
                    Seat(Position(0, 1), Grade.A),
                ),
            )
        }
    }

    @Test
    fun `좌석 갯수는 티켓수와 같다`() {
        assertDoesNotThrow {
            Reservation(
                1,
                Screen.NULL,
                Ticket(2),
                Seats(
                    Seat(Position(0, 0), Grade.S),
                    Seat(Position(0, 1), Grade.A),
                ),
            )
        }
    }
}
