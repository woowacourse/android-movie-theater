package woowacourse.movie.repository

import android.content.Context
import woowacourse.movie.db.history.ReservationHistoryDatabase
import woowacourse.movie.model.ticket.ReservationTicket

class ReservationTicketRepositoryImpl(context: Context) : ReservationTicketRepository {
    private val historyDao = ReservationHistoryDatabase.getInstance(context).reservationDao()

    override fun loadReservationTickets(): List<ReservationTicket> {
        return historyDao.findReservations()
    }

    override fun saveReservationTicket(reservationTicket: ReservationTicket) {
        historyDao.saveReservationTicket(reservationTicket)
    }

    override fun clearReservationTickets() {
        historyDao.clearReservations()
    }
}
