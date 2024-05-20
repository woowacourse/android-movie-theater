package woowacourse.movie.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.data.model.ScreenData

class ReservationTest {
    @Test
    fun `throw exception when the count of seats is not equal to count of ticket`() {
        assertThrows<IllegalArgumentException> {
            Reservation(
                1,
                ScreenData.NULL,
                Ticket(1),
                Seats(
                    Seat(Position(0, 0), Grade.S),
                    Seat(Position(0, 1), Grade.A),
                ),
                DateTime.NULL,
                Theater.NULL,
            )
        }
    }

    @Test
    fun `the count of seats is equal to count of ticket`() {
        assertDoesNotThrow {
            Reservation(
                1,
                ScreenData.NULL,
                Ticket(2),
                Seats(
                    Seat(Position(0, 0), Grade.S),
                    Seat(Position(0, 1), Grade.A),
                ),
                DateTime.NULL,
                Theater.NULL,
            )
        }
    }
}
