package woowacourse.movie.data

import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalTime

class FakeTicketRepository : TicketRepository {
    private val tickets = mutableMapOf<Long, Ticket>()
    private var id = 0L

    override fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String
    ): Long {
        tickets[id] = Ticket(id, movieId, screeningDate, screeningTime, selectedSeats, theaterName)
        return id++
    }

    override fun find(id: Long): Ticket {
        return tickets[id] ?: throw IllegalArgumentException()
    }

    override fun findAll(): List<Ticket> {
        return tickets.map { it.value }
    }

    override fun deleteAll() {
        tickets.clear()
    }
}
