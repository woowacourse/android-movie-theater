package woowacourse.movie.data.mapper

import woowacourse.movie.data.entity.BookedTicketEntity
import woowacourse.movie.domain.model.theater.BookedTicket

object BookedTicketMapper {
    fun toModel(bookedTicketEntity: BookedTicketEntity): BookedTicket =
        with(bookedTicketEntity) {
            BookedTicket(
                movieName,
                headcount,
                dateTime,
                seats,
                theaterName,
            )
        }

    fun toEntity(
        bookedTicket: BookedTicket,
        uid: Int = 0,
    ): BookedTicketEntity =
        with(bookedTicket) {
            BookedTicketEntity(
                uid = uid,
                movieName = movieName,
                headcount = headcount,
                dateTime = dateTime,
                seats = seats,
                theaterName = theaterName,
            )
        }
}
