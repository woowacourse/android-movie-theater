package woowacourse.domain.reservation

import woowacourse.domain.ticket.Ticket
import woowacourse.domain.util.CgvResult

interface ReservationRepository {

    fun getReservations(): List<Reservation>

    fun getReservation(id: Long): CgvResult<Reservation>

    fun makeReservation(tickets: Set<Ticket>): Reservation
}
