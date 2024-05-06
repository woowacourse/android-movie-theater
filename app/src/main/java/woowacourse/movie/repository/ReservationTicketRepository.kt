package woowacourse.movie.repository

import woowacourse.movie.model.ticket.ReservationTicket

interface ReservationTicketRepository {

    fun loadReservationTickets(): List<ReservationTicket>

    fun saveReservationTicket(reservationTicket: ReservationTicket)

    fun clearReservationTickets()
}
