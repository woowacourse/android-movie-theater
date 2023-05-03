package woowacourse.data.reservation

import woowacourse.data.reservation.ReservationMapper.toReservation
import woowacourse.data.reservation.ReservationMapper.toReservationEntity
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.ticket.Ticket

class ReservationRepositoryImpl : ReservationRepository {
    override fun getReservations(): List<Reservation> {
        return ReservationDatabase.bookings.mapNotNull { it.toReservation() }
    }

    override fun getReservation(id: Long): Reservation? {
        return ReservationDatabase.selectBooking(id)?.toReservation()
    }

    override fun addReservation(reservation: Reservation) {
        ReservationDatabase.insertBooking(reservation.toReservationEntity())
    }

    override fun makeReservation(tickets: Set<Ticket>): Reservation {
        val reservation = Reservation(tickets, ReservationDatabase.getNewId())
        addReservation(reservation)
        return reservation
    }
}
