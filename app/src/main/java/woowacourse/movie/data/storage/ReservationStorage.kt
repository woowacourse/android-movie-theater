package woowacourse.movie.data.storage

import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.model.ticket.MovieTicket

interface ReservationStorage {
    fun saveMovieTicket(
        movieTicket: MovieTicket,
        onComplete: (reservationId: Long) -> Unit,
    )

    fun getMovieTicket(
        reservationId: Long,
        onComplete: (movieTicket: MovieTicketEntity?) -> Unit,
    )

    fun getAllMovieTickets(onComplete: (movieTickets: List<MovieTicketEntity>) -> Unit)
}
