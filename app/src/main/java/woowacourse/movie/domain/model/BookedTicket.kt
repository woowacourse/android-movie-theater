package woowacourse.movie.domain.model

import java.io.Serializable
import woowacourse.movie.data.BookedTicketEntity

class BookedTicket(
    val theaterName: String,
    val movieTitle: String,
    val movieSchedule: MovieSchedule,
    val headcount: Headcount,
) : Serializable {
    fun totalPrice(): Int = movieSchedule.seats.totalPrice()
}

fun BookedTicket.toBookedTicketEntity(): BookedTicketEntity {
    return BookedTicketEntity(
        theaterName = theaterName,
        movieTitle = movieTitle,
        screeningDateTime = movieSchedule.screeningDateTime,
        selectSeats = movieSchedule.seats.reservingSeats,
        headcount = headcount.count,
    )
}
