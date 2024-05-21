package woowacourse.movie.data.source

import woowacourse.movie.data.model.ReservationTicket

interface ReservationTicketDao {
    fun insert(reservationTicket: ReservationTicket): Long

    fun findReservationById(id: Int): ReservationTicket

    fun findAll(): List<ReservationTicket>
}
