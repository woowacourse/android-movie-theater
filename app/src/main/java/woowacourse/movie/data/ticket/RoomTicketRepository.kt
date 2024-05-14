package woowacourse.movie.data.ticket

import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalTime
import kotlin.concurrent.thread

class RoomTicketRepository(private val ticketDao: TicketDao) : TicketRepository {
    override fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ): Long {
        val ticket =
            Ticket(
                movieId = movieId,
                screeningDate = screeningDate,
                screeningTime = screeningTime,
                selectedSeats = selectedSeats,
                theaterName = theaterName,
            )
        var id = -1L
        thread {
            id = ticketDao.insert(ticket)
        }.join()
        return id
    }

    override fun find(id: Long): Ticket {
        var result: Result<Ticket>? = null
        thread {
            result = runCatching { ticketDao.find(id) }
        }.join()
        return result?.getOrThrow() ?: throw IllegalArgumentException()
    }

    override fun findAll(): List<Ticket> {
        var tickets: List<Ticket> = emptyList()
        thread {
            tickets = ticketDao.getAll()
        }.join()
        return tickets
    }

    override fun deleteAll() {
        thread {
            ticketDao.deleteAll()
        }.join()
    }
}
