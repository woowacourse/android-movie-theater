package woowacourse.movie.repository

import android.content.Context
import woowacourse.movie.db.history.ReservationHistoryDatabase
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket

class ReservationTicketRepositoryImpl(context: Context) : ReservationTicketRepository {
    private val historyDao = ReservationHistoryDatabase.getInstance(context).reservationDao()
    private val theaterDao = TheaterDao()
    private val movieDao = ScreeningDao()

    override fun loadReservationTickets(): List<ReservationTicket> {
        return historyDao.findReservations()
    }

    override fun saveReservationTicket(ticket: Ticket): Long {
        val reservationTicket =
            ticket.toReservationTicket(
                movieTitle = movieDao.find(ticket.movieId).title,
                theaterName = theaterDao.find(ticket.theaterId).theaterName,
            )
        return historyDao.saveReservationTicket(reservationTicket)
    }

    override fun findReservationTicket(ticketId: Long): ReservationTicket? {
        return historyDao.findReservationById(ticketId)
    }
}
