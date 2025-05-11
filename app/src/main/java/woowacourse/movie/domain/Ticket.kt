package woowacourse.movie.domain

import woowacourse.movie.domain.movieseat.Seats
import java.time.LocalDateTime

data class Ticket(
    val title: String,
    val date: LocalDateTime,
    val personnel: Int,
    val theaterName: String,
    val seats: Seats = Seats(mutableSetOf()),
)
