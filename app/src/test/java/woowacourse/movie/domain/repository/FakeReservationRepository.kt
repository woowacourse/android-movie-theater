package woowacourse.movie.domain.repository

import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation

class FakeReservationRepository : ReservationRepository {
    private val reservations =
        mutableListOf(
            Reservation.NULL,
        )

    private val reservationTickets =
        mutableListOf(
            ReservationTicket.NULL,
        )

    private val timeReservations =
        mutableListOf(
            TimeReservation.NULL,
        )

    override fun savedReservationId(
        screenData: ScreenData,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long> =
        runCatching {
            val id = reservations.size + 1
            reservations.add(Reservation(id, screenData, Ticket(seats.count()), seats, dateTime, theater))
            id.toLong()
        }

    override fun savedTimeReservationId(
        screenData: ScreenData,
        count: Int,
        dateTime: DateTime,
    ): Result<Int> =
        runCatching {
            val id = timeReservations.size + 1
            timeReservations.add(
                TimeReservation(
                    id = timeReservations.size + 1,
                    screenData = screenData,
                    ticket = Ticket(count),
                    dateTime = dateTime,
                ),
            )
            id
        }

    override fun loadAllReservationHistory(): Result<List<ReservationTicket>> = runCatching { reservationTickets }

    override fun loadTimeReservation(timeReservationId: Int): TimeReservation =
        timeReservations.find { it.id == timeReservationId }
            ?: throw NoSuchElementException("TimeReservation not found with timeReservationId: $timeReservationId.")

    override fun findById(id: Int): Result<ReservationTicket> =
        runCatching {
            val reservationTicket = reservationTickets.find { it.id == id }
            reservationTicket ?: throw NoSuchElementException()
        }
}
