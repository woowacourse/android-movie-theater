package woowacourse.movie.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.FakeReservationTicketDao
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.OfflineReservationRepository
import woowacourse.movie.local.source.ReservationTicketRoomDao

class OfflineReservationRepositoryTest {
    private lateinit var fakeDao: ReservationTicketRoomDao
    private lateinit var repository: ReservationRepository

    @BeforeEach
    fun setUp() {
        fakeDao = FakeReservationTicketDao()
        repository = OfflineReservationRepository(fakeDao)
    }

    @Test
    fun `오프라인 예약 저장 테스트`() {
        // given
        val reservation = Reservation.NULL

        // when
        val result =
            repository.savedReservationId(reservation.screenData, reservation.seats, reservation.dateTime, Theater.NULL)
                .getOrThrow()

        // then
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `오프라인 예약 이력 불러오기 테스트`() {
        // given
        val seats1 =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(0, 1), Grade.S),
            )
        val seats2 =
            Seats(
                Seat(Position(1, 2), Grade.A),
                Seat(Position(2, 2), Grade.B),
            )
        val seats3 =
            Seats(
                Seat(Position(1, 2), Grade.S),
                Seat(Position(0, 3), Grade.B),
            )

        repository.savedReservationId(ScreenData.NULL, seats1, DateTime.NULL, Theater.NULL)
        repository.savedReservationId(ScreenData.NULL, seats2, DateTime.NULL, Theater.NULL)
        repository.savedReservationId(ScreenData.NULL, seats3, DateTime.NULL, Theater.NULL)

        // when
        val result = repository.loadAllReservationHistory().getOrThrow()

        // then
        assertThat(result).isEqualTo(
            listOf(
                ReservationTicket(1, ScreenData.NULL, DateTime.NULL.date, DateTime.NULL.time, seats1, Theater.NULL),
                ReservationTicket(2, ScreenData.NULL, DateTime.NULL.date, DateTime.NULL.time, seats2, Theater.NULL),
                ReservationTicket(3, ScreenData.NULL, DateTime.NULL.date, DateTime.NULL.time, seats3, Theater.NULL),
            ),
        )
    }

    @Test
    fun `오프라인 시간 예약 저장 & 불러오기 테스트`() {
        // given
        val timeReservationId = repository.savedTimeReservationId(ScreenData.NULL, 2, DateTime.NULL).getOrThrow()

        // when
        val actual = repository.loadTimeReservation(timeReservationId)

        // then
        val expected = TimeReservation(timeReservationId, ScreenData.NULL, Ticket(2), DateTime.NULL)
        assertThat(actual).isEqualTo(expected)
    }
}
