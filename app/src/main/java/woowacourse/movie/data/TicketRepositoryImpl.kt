package woowacourse.movie.data

import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

class TicketRepositoryImpl(private val ticketDao: TicketDao) : TicketRepository {
    override fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ): Long {
        return ticketDao.insert(
            Ticket(
                movieId,
                screeningDate,
                screeningTime,
                selectedSeats,
                theaterName
            )
        )
    }

    override fun find(id: Long): Ticket {
        return ticketDao.find(id)
    }

    override fun findAll(): List<Ticket> {
        return ticketDao.getAll()
    }
}
