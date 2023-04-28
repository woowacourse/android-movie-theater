package woowacourse.movie.util

import woowacourse.movie.ticket.Seat
import woowacourse.movie.ticket.Ticket
import java.time.LocalDateTime

fun Ticket(seat: Seat): Ticket =
    Ticket(
        movieId = 0,
        movieTitle = movieName,
        bookedDateTime = LocalDateTime.of(2024, 3, 1, 16, 0),
        seat = seat
    )
