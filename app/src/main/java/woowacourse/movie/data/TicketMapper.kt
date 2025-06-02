package woowacourse.movie.data

import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Seats
import woowacourse.movie.domain.movieseat.toSeatString
import woowacourse.movie.domain.movieseat.toSeats
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Ticket.toEntity(): TicketInfo {
    return TicketInfo(
        tid = 0,
        movieTitle = title,
        dateTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        theaterName = theaterName,
        personnel = personnel,
        seats = seats.toSeatString(),
    )
}

fun TicketInfo.toDomain(): Ticket {
    return Ticket(
        title = movieTitle.orEmpty(),
        dateTime = LocalDateTime.parse(dateTime),
        personnel = personnel ?: 0,
        theaterName = theaterName.orEmpty(),
        seats = seats?.toSeats() ?: Seats(),
    )
}
