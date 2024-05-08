package woowacourse.movie.data

import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

interface TicketRepository {
    fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        selectedSeats: MovieSelectedSeats,
        theaterName: String,
    ): Long

    fun find(id: Long): Ticket

    fun findAll(): List<Ticket>

    fun deleteAll()
}
