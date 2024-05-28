package woowacourse.movie.domain.repository

import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.data.source.ReservationTicketDao
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation

class OfflineReservationRepository(private val reservationTicketDao: ReservationTicketDao) : ReservationRepository {
    override fun savedReservationId(
        screenData: ScreenData,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long> =
        runCatching {
            reservationTicketDao.insert(
                reservationTicket =
                    ReservationTicket(
                        screenData = screenData,
                        date = dateTime.date,
                        time = dateTime.time,
                        seats = seats,
                        theater = theater,
                    ),
            )
        }

    override fun savedTimeReservationId(
        screenData: ScreenData,
        count: Int,
        dateTime: DateTime,
    ): Result<Int> =
        runCatching {
            val id = timeReservation.size + 1
            timeReservation.add(TimeReservation(id, screenData, Ticket(count), dateTime))
            id
        }

    override fun loadAllReservationHistory(): Result<List<ReservationTicket>> =
        runCatching {
            reservationTicketDao.findAll()
        }

    override fun loadTimeReservation(timeReservationId: Int): TimeReservation =
        timeReservation.find {
            it.id == timeReservationId
        } ?: throw NoSuchElementException("TimeReservation not found with timeReservationId: $timeReservationId.")

    override fun findById(id: Int): Result<ReservationTicket> =
        runCatching {
            reservationTicketDao.findReservationById(id)
        }

    companion object {
        private val timeReservation = mutableListOf<TimeReservation>()
    }
}
