package woowacourse.movie.data.ticket

import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

class TicketRepositoryImpl(private val ticketDao: TicketDao) :
    TicketRepository {
    override fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ): Long {
        return ticketDao.insert(
            Ticket(
                movieId = movieId,
                screeningDate = screeningDate,
                screeningTime = screeningTime,
                selectedSeats = selectedSeats,
                theaterName = theaterName,
            ),
        )
    }

    override fun find(id: Long): Ticket {
        return ticketDao.find(id)
    }

    override fun findAll(): List<Ticket> {
        return ticketDao.getAll()
    }

    override fun deleteAll() {
        ticketDao.deleteAll()
    }
}
