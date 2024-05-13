package woowacourse.movie.repository

import woowacourse.movie.database.ReservationData
import woowacourse.movie.model.Ticket

interface ReservationRepository {
    fun loadReservationList(): List<ReservationData>

    fun saveReservationData(ticket: Ticket): Long

    fun findReservationData(ticketId: Long): ReservationData?

    fun findTicket(ticketId: Long): Ticket?
}
