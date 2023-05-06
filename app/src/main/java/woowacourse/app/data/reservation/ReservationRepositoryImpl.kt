package woowacourse.app.data.reservation

import woowacourse.app.data.reservation.ReservationMapper.toReservation
import woowacourse.app.data.reservation.ReservationMapper.toReservationEntity
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.ticket.Ticket

class ReservationRepositoryImpl(private val reservationDataSource: ReservationDataSource) :
    ReservationRepository {
    override fun getReservations(): List<Reservation> {
        return reservationDataSource.getReservationEntities().mapNotNull { it.toReservation() }
    }

    override fun getReservation(id: Long): Reservation? {
        return reservationDataSource.getReservationEntity(id)?.toReservation()
    }

    override fun addReservation(reservation: Reservation) {
        reservationDataSource.addReservationEntity(reservation.toReservationEntity())
    }

    override fun makeReservation(tickets: Set<Ticket>): Reservation {
        val reservation = Reservation(tickets, reservationDataSource.getNewReservationId())
        addReservation(reservation)
        return reservation
    }
}
