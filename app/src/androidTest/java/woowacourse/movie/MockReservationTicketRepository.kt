package woowacourse.movie

import android.content.Context
import woowacourse.movie.db.history.ReservationHistoryDatabase
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.repository.ReservationTicketRepository

class MockReservationTicketRepository(context: Context) : ReservationTicketRepository {
    private val theaterDao = TheaterDao()
    private val movieDao = ScreeningDao()
    private val historyDao = MockReservationHistoryDatabase.getInstance(context).reservationHistoryDao()

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

    fun clearReservationTickets(){
        historyDao.clearReservations()
    }
}
