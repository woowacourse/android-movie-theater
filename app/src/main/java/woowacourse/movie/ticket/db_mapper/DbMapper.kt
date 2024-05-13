package woowacourse.movie.ticket.db_mapper

import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.model.Ticket

object DbMapper {
    fun mapTicketDb(ticket: Ticket): DbTicket {
        val movieTitle = CommonDataSource.movieList.first { it.id == ticket.movieId }.title
        val seatsFormatted = ticket.seats.joinToString { it.coordinate }
        val theaterName = TheaterData.theaters.first { it.id == ticket.theaterId }.name
        return DbTicket(
            0,
            movieTitle,
            ticket.screeningDate,
            ticket.screeningTime,
            ticket.seatsCount.number,
            seatsFormatted,
            theaterName,
            ticket.price,
        )
    }
}
