package woowacourse.movie.util

import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat
import woowacourse.movie.ticket.SeatRank
import woowacourse.movie.ticket.Ticket
import java.time.LocalDateTime

fun Ticket(screeningTime: LocalDateTime): Ticket =
    Ticket(
        movieId = 0,
        movieTitle = movieName,
        bookedDateTime = screeningTime,
        seat = Seat(
            rank = SeatRank.B,
            position = Position(1, 1)
        )
    )
