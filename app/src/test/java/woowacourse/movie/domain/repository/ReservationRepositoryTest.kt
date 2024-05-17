package woowacourse.movie.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation

class ReservationRepositoryTest {
    private lateinit var repository: ReservationRepository

    @BeforeEach
    fun setUp() {
        repository = FakeReservationRepository()
    }

    @Test
    fun `예약을 id로 찾는다`() {
        val reservation = repository.findById(-1).getOrThrow()
        assertThat(reservation).isEqualTo(
            Reservation(
                id = -1,
                screen = Screen.NULL,
                ticket = Ticket(1),
                seats =
                    Seats(
                        Seat(Position(0, 0), Grade.S),
                    ),
            ),
        )
    }

    @Test
    fun `시간예약 id로 시간예약을 찾는다`() {
        val timeReservation = repository.loadTimeReservation(0)

        assertThat(timeReservation).isEqualTo(TimeReservation.NULL)
    }
}
