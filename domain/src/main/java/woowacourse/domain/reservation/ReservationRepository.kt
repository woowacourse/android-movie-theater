package woowacourse.domain.reservation

import woowacourse.domain.ticket.Ticket

interface ReservationRepository {

    fun getReservations(): List<Reservation>

    fun getReservation(id: Long): Reservation?

    fun makeReservation(tickets: Set<Ticket>): Reservation
}
